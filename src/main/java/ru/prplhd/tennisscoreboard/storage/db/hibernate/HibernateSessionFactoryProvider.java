package ru.prplhd.tennisscoreboard.storage.db.hibernate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateSessionFactoryProvider {

    private static final String ENV_DB_USER = "DB_USER";
    private static final String ENV_DB_PASSWORD = "DB_PASSWORD";

    private static final String PROP_HIKARI_USER = "hibernate.hikari.username";
    private static final String PROP_HIKARI_PASSWORD = "hibernate.hikari.password";

    public static Configuration createConfiguration(Class<?>... entities) {
        if (entities == null || entities.length == 0) {
            throw new IllegalArgumentException("At least one entity class is required");
        }

        Configuration configuration = new Configuration();
        for (Class<?> entity : entities) {
            if (entity == null) {
                throw new IllegalArgumentException("Entity class must not be null");
            }

            configuration.addAnnotatedClass(entity);
        }
        configuration.configure();

        String dbUser = System.getenv(ENV_DB_USER);
        String dbPassword = System.getenv(ENV_DB_PASSWORD);

        if (dbUser != null) {
            configuration.setProperty(PROP_HIKARI_USER, dbUser);
            log.info("DB override applied: {} from env {}", PROP_HIKARI_USER, ENV_DB_USER);
        }

        if (dbPassword != null) {
            configuration.setProperty(PROP_HIKARI_PASSWORD, dbPassword);
            log.info("DB override applied: {} from env {}", PROP_HIKARI_PASSWORD, ENV_DB_PASSWORD);

        }

        return configuration;
    }

    public static SessionFactory createSessionFactory(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration must not be null");
        }

        return configuration.buildSessionFactory();

    }
}
