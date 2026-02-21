package ru.prplhd.tennisscoreboard.storage.db.hibernate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateSessionFactoryProvider {

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

        return configuration;
    }

    public static SessionFactory createSessionFactory(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration must not be null");
        }

        return configuration.buildSessionFactory();

    }
}
