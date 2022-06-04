package de.htw_berlin.imi.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JdbcSampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(JdbcSampleApplication.class, args);
    }
}
