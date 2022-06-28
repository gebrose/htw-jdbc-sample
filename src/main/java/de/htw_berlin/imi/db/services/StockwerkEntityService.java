package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Stockwerk;
import de.htw_berlin.imi.db.web.StockwerkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

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
public class StockwerkEntityService extends AbstractEntityService<Stockwerk> {

    private static final String FIND_ALL_QUERY = """
                SELECT
                    s.id             AS id
                   ,s.geschossnummer AS geschossnummer
                   ,r.id             AS room_id
                FROM uni.stockwerke s
                LEFT OUTER JOIN
                    uni.Raeume r ON r.stockwerk = s.id
            """;
    private static final String INSERT_QUERY = """
            INSERT INTO uni.stockwerke (id, geschossnummer)
                VALUES (?, ?);
            """;
    private static final String UPDATE_QUERY = """
            UPDATE uni.stockwerke SET geschossnummer = ?
                WHERE id = ?;
            """;
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE s.ID = ?";

    private BueroRaumEntityService bueroRaumEntityService;

    @Override
    public List<Stockwerk> findAll() {
        final Map<Long, Stockwerk> resultsById = new HashMap<>();
        log.debug("query: {}", FIND_ALL_QUERY);
        try (final Connection connection = getConnection(true);
             final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                getFloorWithRooms(resultSet, resultsById);
            }
        } catch (final Exception e) {
            log.error("Problem finding bueros {}", e.getMessage());
        }
        return new ArrayList<>(resultsById.values());
    }

    @Override
    public Optional<Stockwerk> findById(final long id) {
        log.debug("query: {}", FIND_BY_ID_QUERY + id);
        try (final Connection connection = getConnection(true);
             final PreparedStatement basePreparedStatement = getPreparedStatement(connection, FIND_BY_ID_QUERY)) {
            basePreparedStatement.setLong(1, id);
            final ResultSet resultSet = basePreparedStatement.executeQuery();
            final Map<Long, Stockwerk> result = new HashMap<>();
            while (resultSet.next()) {
                getFloorWithRooms(resultSet, result);
            }
            return Optional.ofNullable(result.get(id));
        } catch (final Exception e) {
            log.error("Problem finding buero by id {}", e.getMessage());
        }
        return Optional.empty();
    }

    private void getFloorWithRooms(final ResultSet resultSet, final Map<Long, Stockwerk> results) throws SQLException {
        final long id = resultSet.getLong("id");
        final Stockwerk entity = getEntity(resultSet, results, id);
        final long roomId = resultSet.getLong("room_id");
        if (roomId != 0) {
            bueroRaumEntityService
                    .findById(roomId)
                    .ifPresent(r -> entity.getRaeume().add(r));
        }
    }

    private Stockwerk getEntity(final ResultSet resultSet, final Map<Long, Stockwerk> results, final long id) throws SQLException {
        final Stockwerk entity;
        if (results.containsKey(id)) {
            entity = results.get(id);
        } else {
            entity = new Stockwerk(id);
            entity.setGeschossnummer(resultSet.getInt("geschossnummer"));
            results.put(id, entity);
        }
        return entity;
    }

    @Override
    public Stockwerk create() {
        return new Stockwerk(idGenerator.generate());
    }

    @Override
    public void save(final Stockwerk e) {
        log.debug("insert: {}", INSERT_QUERY);
        try (final Connection connection = getConnection(false)) {
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement = getPreparedStatement(connection, INSERT_QUERY)) {
                basePreparedStatement.setLong(1, e.getId());
                basePreparedStatement.setInt(2, e.getGeschossnummer());
                final int update = basePreparedStatement.executeUpdate();
                if (update != 1) {
                    throw new SQLException("Could not create (room) part");
                }
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
    public void update(final Stockwerk e) {
        log.debug("update: {}", e);
        final double start = System.currentTimeMillis();
        try (final Connection connection = getConnection(false)) {
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement = getPreparedStatement(connection, UPDATE_QUERY)) {
                basePreparedStatement.setInt(1, e.getGeschossnummer());
                basePreparedStatement.setLong(2, e.getId());
                final int update = basePreparedStatement.executeUpdate();
                if (update != 1) {
                    throw new SQLException("Could not update floor");
                }
                connection.commit();
            } catch (final SQLException ex) {
                log.error("Error creating floor, aborting {}", ex.getMessage());
                connection.rollback();
                throw new RuntimeException(ex);
            }
        } catch (final SQLException ex) {
            log.error("Could not get connection.");
            throw new RuntimeException(ex);
        }
        log.info("Update finished in {} ms.", System.currentTimeMillis() - start);
    }

    @Override
    public void delete(final Stockwerk entity) {
        final double start = System.currentTimeMillis();
        log.debug("delete: {}", entity);
        try (final Connection connection = getConnection(false)) {
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement =
                         getPreparedStatement(connection, "DELETE FROM uni.Stockwerke WHERE id = ?")) {
                basePreparedStatement.setLong(1, entity.getId());
                basePreparedStatement.executeUpdate();
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

    public Stockwerk createFrom(final StockwerkDto template) {
        final Stockwerk stockwerk = create();
        stockwerk.setGeschossnummer(template.getGeschossnummer());
        save(stockwerk);
        return stockwerk;
    }

    @Autowired
    public void setBueroRaumEntityService(final BueroRaumEntityService bueroRaumEntityService) {
        this.bueroRaumEntityService = bueroRaumEntityService;
    }
}
