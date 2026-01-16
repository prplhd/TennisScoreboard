package ru.prplhd.tennisscoreboard.persistence.bootstrap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseMigrator {
    public static void runMigrations(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration must not be null");
        }

        Properties props = configuration.getProperties();
        String url = props.getProperty("hibernate.hikari.jdbcUrl");
        String username = props.getProperty("hibernate.hikari.username");
        String password = props.getProperty("hibernate.hikari.password");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("DB url is missing. Check hibernate.cfg.xml property 'hibernate.hikari.jdbcUrl'");
        }

        Flyway flyway = Flyway.configure()
                .dataSource(url, username, password)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }
}
