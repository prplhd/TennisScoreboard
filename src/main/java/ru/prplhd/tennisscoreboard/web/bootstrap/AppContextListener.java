package ru.prplhd.tennisscoreboard.web.bootstrap;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.FlywayException;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;
import ru.prplhd.tennisscoreboard.storage.db.migrator.DatabaseMigrator;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.HibernateSessionFactoryProvider;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;
import ru.prplhd.tennisscoreboard.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.repository.MatchRepositoryImpl;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.repository.PlayerRepositoryImpl;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        SessionFactory sessionFactory;

        try {
            log.info("Building Hibernate Configuration...");
            Configuration configuration = HibernateSessionFactoryProvider
                    .createConfiguration(PlayerEntity.class, MatchEntity.class);

            log.info("Starting Flyway migration...");
            DatabaseMigrator.runMigrations(configuration);

            log.info("Building Hibernate SessionFactory...");
            sessionFactory = HibernateSessionFactoryProvider
                    .createSessionFactory(configuration);
            context.setAttribute(ServletContextKeys.SESSION_FACTORY, sessionFactory);

            log.info("Startup completed successfully");

        } catch (FlywayException e) {
            log.error("Startup failed: database migration failed", e);
            throw new IllegalStateException(e);

        } catch (HibernateException e) {
            log.error("Startup failed: Hibernate initialization failed", e);
            throw new IllegalStateException(e);

        } catch (RuntimeException e) {
            log.error("Startup failed: unexpected error", e);
            throw new IllegalStateException(e);
        }

        MatchRepository matchRepository = new MatchRepositoryImpl(sessionFactory);
        PlayerRepository  playerRepository = new PlayerRepositoryImpl(sessionFactory);

        // Это удалить
        Player player1 = new Player(1, "Chel");
        Player player2 = new Player(2, "Guy");

        Match match = new Match(player1, player2);

        context.setAttribute("match", match);
        // Это удалить

        context.setAttribute(ServletContextKeys.MATCH_REPOSITORY, matchRepository);
        context.setAttribute(ServletContextKeys.PLAYER_REPOSITORY, playerRepository);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            SessionFactory sessionFactory = (SessionFactory) context.getAttribute(ServletContextKeys.SESSION_FACTORY);

            log.info("Shutting down Hibernate SessionFactory...");

            if (sessionFactory == null) {
                log.warn("SessionFactory not found in ServletContext. Skipping shutdown.");
            } else {
                sessionFactory.close();
                context.removeAttribute(ServletContextKeys.SESSION_FACTORY);

                log.info("Hibernate SessionFactory shut down successfully.");
            }

        } catch (Exception e) {
            log.error("Error while closing Hibernate SessionFactory during application shutdown.", e);
        }
    }
}
