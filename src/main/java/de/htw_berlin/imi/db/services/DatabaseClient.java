package de.htw_berlin.imi.db.services;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Convenience class that provides access to pooled DB connections.
 */
@Slf4j
@Service
public class DatabaseClient {

    private static final ComboPooledDataSource POOLED_DATA_SOURCE = new ComboPooledDataSource();

    @Value("${jdbc.connectionString}")
    private String jdbcConnectionString;

    @Value("${jdbc.driverClass}")
    private String driverClass;

    @Value("${jdbc.userName}")
    private String userName;

    @Value("${jdbc.password}")
    private String password;

    @PostConstruct
    void init() throws PropertyVetoException {
        POOLED_DATA_SOURCE.setDriverClass(driverClass);
        POOLED_DATA_SOURCE.setJdbcUrl(jdbcConnectionString);
        POOLED_DATA_SOURCE.setUser(userName);
        POOLED_DATA_SOURCE.setPassword(password);
        POOLED_DATA_SOURCE.setInitialPoolSize(5);
        POOLED_DATA_SOURCE.setMaxPoolSize(30);
    }

    protected Connection getConnection(final boolean readOnly) throws SQLException {
        final Connection connection = POOLED_DATA_SOURCE.getConnection();
        connection.setReadOnly(readOnly);
        return connection;
    }

    protected PreparedStatement getPreparedStatement(final Connection connection, final String insertBaseQuery) throws SQLException {
        return connection.prepareStatement(insertBaseQuery);
    }
}


