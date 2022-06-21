package de.htw_berlin.imi.db.services;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class StatementRunner extends DatabaseClient {

    Thread run(final String sql, final int loops) {
        final Thread t = new Thread(() -> execute(sql, loops, 7));
        t.start();
        return t;
    }

    private void execute(final String sql, final int loops, final int waitTimeInMillis) {
        try (final Connection connection = getConnection(true)) {
            final PreparedStatement preparedStatement;
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            preparedStatement = connection.prepareStatement(sql);
            for (int localLoops = 0; localLoops < loops; localLoops++) {
                System.out.println("thread:" + Thread.currentThread().getName() + " l: " + localLoops);
                preparedStatement.executeUpdate();
                Thread.sleep(waitTimeInMillis);
            }
        } catch (final InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
