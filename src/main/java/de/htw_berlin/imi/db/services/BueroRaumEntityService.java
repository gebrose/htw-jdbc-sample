package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.BueroRaum;
import de.htw_berlin.imi.db.web.BueroDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements the DAO (data access object) pattern for BueroRaum.
 * <p>
 * Classes annotated with @Service can be injected using @Autowired
 * in other Spring components.
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

    private static final String INSERT_BASE_QUERY = """
            INSERT INTO uni.Raeume (id, name, raumnummer, flaeche, raumhoehe)
                VALUES (?, ?, ?, ?, ?);
            """;

    private static final String INSERT_WORK_ROOM = """
            INSERT INTO uni.Arbeitsraeume (id, kapazitaet)
                VALUES (?, ?);
            """;

    private static final String INSERT_OFFICE_ROOM = """
            INSERT INTO uni.Bueroraeume (id)
                VALUES (?);
            """;

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE ID = ";

    @Override
    public List<BueroRaum> findAll() {
        final List<BueroRaum> result = new ArrayList<>();
        try {
            final ResultSet resultSet = query(FIND_ALL_QUERY);
            while (resultSet.next()) {
                result.add(getBueroRaum(resultSet));
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
                return Optional.of(getBueroRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding buero by id {}", e.getMessage());
        }
        return Optional.empty();
    }

    private BueroRaum getBueroRaum(final ResultSet resultSet) throws SQLException {
        final long id = resultSet.getInt("id");
        final BueroRaum entity = new BueroRaum(id);
        entity.setName(resultSet.getString("name"));
        entity.setFlaeche(resultSet.getDouble("flaeche"));
        entity.setHoehe(resultSet.getDouble("hoehe"));
        entity.setKapazitaet(resultSet.getInt("kapazitaet"));
        entity.setRaumnummer(resultSet.getString("raumnummer"));
        return entity;
    }

    @Override
    public BueroRaum create() {
        return new BueroRaum(idGenerator.generate());
    }

    @Override
    public void save(final BueroRaum e) {
        log.debug("insert: {}", INSERT_BASE_QUERY);
        try {
            final Connection connection = getConnection();
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement = getPreparedStatement(connection, INSERT_BASE_QUERY);
                 final PreparedStatement workPreparedStatement = getPreparedStatement(connection, INSERT_WORK_ROOM);
                 final PreparedStatement officePreparedStatement = getPreparedStatement(connection, INSERT_OFFICE_ROOM)) {

                createBaseClassPart(e, basePreparedStatement);
                createWorkRoomPart(e, workPreparedStatement);
                createOfficePart(e, officePreparedStatement);
                connection.commit();
            } catch (final SQLException ex) {
                log.error("Error creating office, aborting {}", ex.getMessage());
                connection.rollback();
                throw new RuntimeException(ex);
            }
        } catch (final SQLException ex) {
            log.error("Could not get connection.");
            throw new RuntimeException(ex);
        }
    }

    private void createOfficePart(final BueroRaum e, final PreparedStatement officePreparedStatement) throws SQLException {
        // TODO set parameters
        final int update = officePreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (office) part");
        }
    }

    private void createWorkRoomPart(final BueroRaum e, final PreparedStatement workPreparedStatement) throws SQLException {
        // TODO set parameters
        final int update = workPreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (work room) part");
        }
    }

    private void createBaseClassPart(final BueroRaum e, final PreparedStatement basePreparedStatement) throws SQLException {
        // TODO set parameters
        final int update = basePreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (room) part");
        }
    }

    public BueroRaum createFrom(final BueroDto template) {
        final BueroRaum bueroRaum = create();
        bueroRaum.setName(template.getName());
        bueroRaum.setRaumnummer(template.getRaumnummer());
        bueroRaum.setKapazitaet(template.getKapazitaet());
        // TODO initialize missing fields
        save(bueroRaum);
        return bueroRaum;
    }
}
