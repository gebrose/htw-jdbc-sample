package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.BueroRaum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements the DAO (data access object) pattern for BueroRaum.
 * <p>
 * Classes annotated with @Service can be injected using @Autowired in other Spring components.
 * <p>
 * Classes annotated with @Slf4j have access to loggers.
 */
@Service
@Slf4j
public class BueroRaumEntityService extends AbstractEntityService<BueroRaum> {

    private static final String FIND_ALL_QUERY = """
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

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE ID = ";

    @Override
    public List<BueroRaum> findAll() {
        final List<BueroRaum> result = new ArrayList<>();
        try {
            final ResultSet resultSet = query(FIND_ALL_QUERY);
            while (resultSet.next()) {
                result.add(createBueroRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding bueros {}", e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<BueroRaum> findById(final long id) {
        try {
            final ResultSet resultSet = query(FIND_BY_ID_QUERY + id);
            if (resultSet.next()) {
                return Optional.of(createBueroRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding buero by id {}", e.getMessage());
        }
        return Optional.empty();
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
