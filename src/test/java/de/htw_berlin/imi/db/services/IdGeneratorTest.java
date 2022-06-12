package de.htw_berlin.imi.db.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IdGeneratorTest {

    @Autowired
    IdGenerator idGenerator;

    @Test
    void getNewId() {
        final long newId = idGenerator.generate();
        assertThat(newId).isGreaterThan(1000);
    }
}