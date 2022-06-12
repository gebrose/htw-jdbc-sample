package de.htw_berlin.imi.db.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * Convenience class that provides access to DB connections and creates JDBC statements.
 */
@Slf4j
@Service
public class DatabaseClient {

    @Value("${jdbc.connectionString}")
    private String jdbcConnectionString;

    protected ResultSet query(final String sql) throws SQLException {
        log.debug("query: {}", sql);
        return createStatement().executeQuery(sql);
    }

    protected Statement createStatement() throws SQLException {
        return getConnection().createStatement();
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcConnectionString);
    }

    protected PreparedStatement getPreparedStatement(final Connection connection, final String insertBaseQuery) throws SQLException {
        return connection.prepareStatement(insertBaseQuery);
    }
}


