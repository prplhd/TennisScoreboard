package ru.prplhd.tennisscoreboard.web;

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
import ru.prplhd.tennisscoreboard.persistence.entity.FinishedMatchEntity;
import ru.prplhd.tennisscoreboard.persistence.entity.PlayerEntity;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {
    public static final String SESSION_FACTORY_ATTRIBUTE = "sessionFactory";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try {
            log.info("Building Hibernate Configuration...");
            Configuration configuration = HibernateSessionFactoryProvider
                    .createConfiguration(PlayerEntity.class, FinishedMatchEntity.class);

            log.info("Starting Flyway migration...");
            DatabaseMigrator.runMigrations(configuration);

            log.info("Building Hibernate SessionFactory...");
            SessionFactory sessionFactory = HibernateSessionFactoryProvider
                    .createSessionFactory(configuration);
            context.setAttribute(SESSION_FACTORY_ATTRIBUTE, sessionFactory);

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            SessionFactory sessionFactory = (SessionFactory) context.getAttribute(SESSION_FACTORY_ATTRIBUTE);

            log.info("Shutting down Hibernate SessionFactory...");

            if (sessionFactory == null) {
                log.warn("SessionFactory not found in ServletContext. Skipping shutdown.");
            } else {
                sessionFactory.close();
                context.removeAttribute(SESSION_FACTORY_ATTRIBUTE);

                log.info("Hibernate SessionFactory shut down successfully.");
            }

        } catch (Exception e) {
            log.error("Error while closing Hibernate SessionFactory during application shutdown.", e);
        }
    }
}
