package de.htw_berlin.imi.db.services;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.beans.PropertyVetoException;
import java.sql.*;

/**
 * Convenience class that provides access to DB connections and creates JDBC statements.
 */
@Slf4j
@Service
public class DatabaseClient {

    @Value("${jdbc.connectionString}")
    private String jdbcConnectionString;

    private ComboPooledDataSource ds = new ComboPooledDataSource();

    @PostConstruct
    public void init() throws PropertyVetoException {
        ds.setDriverClass("org.postgresql.Driver");
        ds.setJdbcUrl(jdbcConnectionString);
        ds.setUser("postgres");
        ds.setPassword("postgres");
        ds.setInitialPoolSize(5);
        ds.setMaxPoolSize(50);
    }

    protected ResultSet query(final String sql, final boolean readOnly) throws SQLException {
        log.debug("query: {}", sql);
        return createStatement(readOnly).executeQuery(sql);
    }

    protected Statement createStatement(final boolean readOnly) throws SQLException {
        return getConnection(readOnly).createStatement();
    }

    protected Connection getConnection(final boolean readOnly) throws SQLException {
        final Connection connection = DriverManager.getConnection(jdbcConnectionString);
        connection.setReadOnly(readOnly);
        return connection;
    }

    protected PreparedStatement getPreparedStatement(final Connection connection, final String insertBaseQuery) throws SQLException {
        return connection.prepareStatement(insertBaseQuery);
    }
}


