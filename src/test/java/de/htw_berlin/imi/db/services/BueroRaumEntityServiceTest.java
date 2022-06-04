package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.BueroRaum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BueroRaumEntityServiceTest {

    @Autowired
    BueroRaumEntityService bueroRaumEntityService;

    @Test
    void findAll() {
        final List<BueroRaum> all = bueroRaumEntityService.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all).extracting(BueroRaum::getId).isNotEmpty();
    }

    @Test
    void findById() {
        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(11);
        assertThat(bueroRaumOptional).isPresent();
        assertThat(bueroRaumOptional.get().getName()).isEqualTo("Office#6");
    }

    @Test
    void cannotfindById() {
        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(0);
        assertThat(bueroRaumOptional).isNotPresent();
    }

}