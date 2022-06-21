package de.htw_berlin.imi.db.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@Slf4j
public class IdGenerator extends DatabaseClient {

    private static final String NEXT_ID_QUERY = """
                SELECT nextval('uni.id_sequence') AS id;
            """;

    protected long generate() {
        try (final Statement statement = createStatement(false)) {
            final ResultSet resultSet = statement.executeQuery(NEXT_ID_QUERY);
            resultSet.next();
            return resultSet.getLong("id");
        } catch (final SQLException e) {
            log.error("Could not get new id: {}", e.getMessage());
            return -1;
        }
    }
}
