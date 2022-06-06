package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Entity;
import org.springframework.beans.factory.annotation.Value;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * Base class for all entity services (DAOs).
 *
 * @param <E> the entity class.
 */
public abstract class AbstractEntityService<E extends Entity> {

    @Value("${jdbc.connectionString}")
    private String jdbcConnectionString;

    protected ResultSet query(final String sql) throws SQLException {
        return createStatement().executeQuery(sql);
    }

    private Statement createStatement() throws SQLException {
        return DriverManager.getConnection(jdbcConnectionString).createStatement();
    }

    public abstract List<E> findAll();

    public abstract Optional<E> findById(final long id);
}
