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
import ru.prplhd.tennisscoreboard.persistence.bootstrap.DatabaseMigrator;
import ru.prplhd.tennisscoreboard.persistence.bootstrap.HibernateSessionFactoryProvider;
import ru.prplhd.tennisscoreboard.persistence.entity.Match;
import ru.prplhd.tennisscoreboard.persistence.entity.Player;
import ru.prplhd.tennisscoreboard.persistence.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.persistence.repository.MatchRepositoryImpl;
import ru.prplhd.tennisscoreboard.persistence.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.persistence.repository.PlayerRepositoryImpl;

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
                    .createConfiguration(Player.class, Match.class);

            log.info("Starting Flyway migration...");
            DatabaseMigrator.runMigrations(configuration);

            log.info("Building Hibernate SessionFactory...");
            sessionFactory = HibernateSessionFactoryProvider
                    .createSessionFactory(configuration);
            context.setAttribute(ContextKeys.SESSION_FACTORY, sessionFactory);

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

        context.setAttribute(ContextKeys.MATCH_REPOSITORY, matchRepository);
        context.setAttribute(ContextKeys.PLAYER_REPOSITORY, playerRepository);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            SessionFactory sessionFactory = (SessionFactory) context.getAttribute(ContextKeys.SESSION_FACTORY);

            log.info("Shutting down Hibernate SessionFactory...");

            if (sessionFactory == null) {
                log.warn("SessionFactory not found in ServletContext. Skipping shutdown.");
            } else {
                sessionFactory.close();
                context.removeAttribute(ContextKeys.SESSION_FACTORY);

                log.info("Hibernate SessionFactory shut down successfully.");
            }

        } catch (Exception e) {
            log.error("Error while closing Hibernate SessionFactory during application shutdown.", e);
        }
    }
}
