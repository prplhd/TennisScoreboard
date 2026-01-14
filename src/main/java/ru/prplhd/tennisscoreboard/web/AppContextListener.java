package ru.prplhd.tennisscoreboard.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {
    public static final String SESSION_FACTORY_ATTRIBUTE = "sessionFactory";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try {
            log.info("Initializing Hibernate SessionFactory...");

            Configuration configuration = new Configuration();
            configuration.configure();
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            context.setAttribute(SESSION_FACTORY_ATTRIBUTE, sessionFactory);

            log.info("Hibernate SessionFactory successfully initialized");

        } catch (Exception e) {
            log.error("Failed to initialize Hibernate SessionFactory. Application startup will be aborted.", e);
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
