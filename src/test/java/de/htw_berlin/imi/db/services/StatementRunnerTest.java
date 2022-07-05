package de.htw_berlin.imi.db.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatementRunnerTest extends AbstractEntityServiceTest {

    @Autowired
    StatementRunner statementRunnerOne;

    @Autowired
    StatementRunner statementRunnerTwo;

    @Autowired
    StatementRunner statementRunnerThree;

    @Test
    void concurrentUpdates() throws InterruptedException {
        final Thread tOne = statementRunnerOne.run("UPDATE uni.Raeume SET name = 'one' WHERE id = 1", 15);
        final Thread tTwo = statementRunnerTwo.run("UPDATE uni.Raeume SET name = 'two' WHERE id = 1", 15);
        final Thread tThree = statementRunnerThree.run("UPDATE uni.Raeume SET name = 'three' WHERE id = 1", 15);
        tOne.join();
        tTwo.join();
        tThree.join();
    }

}
