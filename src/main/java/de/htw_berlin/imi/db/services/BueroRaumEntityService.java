package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.BueroRaum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements the DAO (data access object) pattern for BueroRaum.
 * <p>
 * Classes annotated with @Service can be injected using @Autowired in other Spring components.
 * <p>
 * Classes annotated with @Slf4j have access to loggers
 */
@Service
@Slf4j
public class BueroRaumEntityService {

    private static final String ALL_BUEROS_SQL = """
                SELECT
                   id
                   ,name
                   ,raumnummer
                   ,flaeche
                   ,hoehe
                   ,kapazitaet
                   ,stockwerk_id
                FROM uni.v_bueros
            """;
    @Value("${jdbc.connectionString}")
    private String jdbcConnectionString;

    public List<BueroRaum> findAll() {
        final List<BueroRaum> result = new ArrayList<>();
        try {
            final ResultSet resultSet = getResultSet(ALL_BUEROS_SQL);
            while (resultSet.next()) {
                result.add(createBueroRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding bueros {}", e.getCause());
        }
        return result;
    }

    public Optional<BueroRaum> findById(final long id) {
        try {
            final ResultSet resultSet = getResultSet(ALL_BUEROS_SQL + " WHERE ID = " + id);
            if (resultSet.next()) {
                return Optional.of(createBueroRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding bueros {}", e.getCause());
        }
        return Optional.empty();
    }

    private ResultSet getResultSet(final String sql) throws ClassNotFoundException, SQLException {
        return getStatement().executeQuery(sql);
    }

    private Statement getStatement() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(jdbcConnectionString).createStatement();
    }

    private BueroRaum createBueroRaum(final ResultSet resultSet) throws SQLException {
        final long id = resultSet.getInt("id");
        final BueroRaum entity = new BueroRaum(id);
        entity.setName(resultSet.getString("name"));
        entity.setFlaeche(resultSet.getDouble("flaeche"));
        entity.setHoehe(resultSet.getDouble("hoehe"));
        entity.setKapazitaet(resultSet.getInt("kapazitaet"));
        return entity;
    }
}
