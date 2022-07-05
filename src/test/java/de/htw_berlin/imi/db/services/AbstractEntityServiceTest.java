package de.htw_berlin.imi.db.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base class for tests that sets up and deletes
 * the SQL schema and test data for the in-memory test data base
 */
abstract class AbstractEntityServiceTest {

    private static final String DROP_SCHEMA = "DROP SCHEMA uni cascade";

    @Autowired
    DatabaseClient databaseClient;

    @Value("classpath:test-data.sql")
    private Resource dataInitFile;

    @Value("classpath:test-schema.sql")
    private Resource schemaInitFile;

    @BeforeEach
    public void setUp() {
        try {
            final String schemaSql =
                    StreamUtils.copyToString(schemaInitFile.getInputStream(), StandardCharsets.UTF_8);
            final String testDataSql =
                    StreamUtils.copyToString(dataInitFile.getInputStream(), StandardCharsets.UTF_8);
            try (final Connection connection = databaseClient.getConnection(false)) {
                connection.createStatement().execute(schemaSql);
                connection.createStatement().execute(testDataSql);
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        try (final Connection connection = databaseClient.getConnection(false)) {
            connection.createStatement().execute(DROP_SCHEMA);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}