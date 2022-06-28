package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.BueroRaum;
import de.htw_berlin.imi.db.web.BueroDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
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
                FROM uni.v_bueros
            """;

    private static final String INSERT_BASE_QUERY = """
            INSERT INTO uni.Raeume (id, name, raumnummer, flaeche, raumhoehe)
                VALUES (?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_BASE_QUERY = """
            UPDATE uni.Raeume SET name = ?, raumnummer = ?, flaeche = ?, raumhoehe = ?
                WHERE id = ?;
            """;

    private static final String INSERT_WORK_ROOM = """
            INSERT INTO uni.Arbeitsraeume (id, kapazitaet)
                VALUES (?, ?);
            """;


    private static final String UPDATE_WORK_ROOM = """
            UPDATE uni.Arbeitsraeume SET kapazitaet = ?
                WHERE id = ?;
            """;

    private static final String INSERT_OFFICE_ROOM = """
            INSERT INTO uni.Bueroraeume (id)
                VALUES (?);
            """;

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE ID = ";

    @Override
    public List<BueroRaum> findAll() {
        final List<BueroRaum> result = new ArrayList<>();
        log.debug("query: {}", FIND_ALL_QUERY);
        try (final Connection connection = getConnection(true);
             final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
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
        log.debug("query: {}", FIND_BY_ID_QUERY + id);
        try (final Connection connection = getConnection(true);
             final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(FIND_BY_ID_QUERY + id);
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
        try (final Connection connection = getConnection(false)) {
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

    @Override
    public void update(final BueroRaum e) {
        log.debug("update: {}", e);
        final double start = System.currentTimeMillis();
        try (final Connection connection = getConnection(false)) {
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement = getPreparedStatement(connection, UPDATE_BASE_QUERY);
                 final PreparedStatement workPreparedStatement = getPreparedStatement(connection, UPDATE_WORK_ROOM)) {
                updateBasePart(e, basePreparedStatement);
                updateWorkPart(e, workPreparedStatement);
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
        log.info("Update finished in {} ms.", System.currentTimeMillis() - start);
    }

    private void updateWorkPart(final BueroRaum e, final PreparedStatement workPreparedStatement) throws SQLException {
        workPreparedStatement.setInt(1, e.getKapazitaet());
        workPreparedStatement.setLong(2, e.getId());
        final int update = workPreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not update (work room) part");
        }
    }

    private void updateBasePart(final BueroRaum e, final PreparedStatement basePreparedStatement) throws SQLException {
        basePreparedStatement.setString(1, e.getName());
        basePreparedStatement.setString(2, e.getRaumnummer());
        basePreparedStatement.setDouble(3, e.getFlaeche());
        basePreparedStatement.setDouble(4, e.getHoehe());
        basePreparedStatement.setLong(5, e.getId());
        final int update = basePreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not update (room) part");
        }
    }

    @Override
    public void delete(final BueroRaum entity) {
        final double start = System.currentTimeMillis();
        log.debug("delete: {}", entity);
        try (final Connection connection = getConnection(false)) {
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement =
                         getPreparedStatement(connection, "DELETE FROM uni.Raeume WHERE id = ?");
                 final PreparedStatement workPreparedStatement =
                         getPreparedStatement(connection, "DELETE FROM uni.Arbeitsraeume WHERE id = ?");
                 final PreparedStatement officePreparedStatement =
                         getPreparedStatement(connection, "DELETE FROM uni.Bueroraeume WHERE id = ?")) {

                basePreparedStatement.setLong(1, entity.getId());
                basePreparedStatement.executeUpdate();

                workPreparedStatement.setLong(1, entity.getId());
                workPreparedStatement.executeUpdate();

                officePreparedStatement.setLong(1, entity.getId());
                officePreparedStatement.executeUpdate();
                connection.commit();
            } catch (final SQLException ex) {
                log.error("Error deleting office, aborting {}", ex.getMessage());
                connection.rollback();
                throw new RuntimeException(ex);
            }
        } catch (final SQLException ex) {
            log.error("Could not get connection.");
            throw new RuntimeException(ex);
        }
        log.info("Delete finished in {} ms.", System.currentTimeMillis() - start);
    }

    private void createOfficePart(final BueroRaum e, final PreparedStatement officePreparedStatement) throws SQLException {
        officePreparedStatement.setLong(1, e.getId());
        final int update = officePreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (office) part");
        }
    }

    private void createWorkRoomPart(final BueroRaum e, final PreparedStatement workPreparedStatement) throws SQLException {
        workPreparedStatement.setLong(1, e.getId());
        workPreparedStatement.setInt(2, e.getKapazitaet());
        final int update = workPreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (work room) part");
        }
    }

    private void createBaseClassPart(final BueroRaum e, final PreparedStatement basePreparedStatement) throws SQLException {
        basePreparedStatement.setLong(1, e.getId());
        basePreparedStatement.setString(2, e.getName());
        basePreparedStatement.setString(3, e.getRaumnummer());
        basePreparedStatement.setDouble(4, e.getFlaeche());
        basePreparedStatement.setDouble(5, e.getHoehe());
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
