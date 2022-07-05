package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.BueroRaum;
import de.htw_berlin.imi.db.web.BueroDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BueroRaumEntityServiceTest extends AbstractEntityServiceTest {

    @Autowired
    BueroRaumEntityService bueroRaumEntityService;

    @Test
    void findAll() {
        final List<BueroRaum> all = bueroRaumEntityService.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all)
                .extracting(BueroRaum::getId)
                .isNotEmpty();
    }

    @Test
    void findById() {
        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(10);
        assertThat(bueroRaumOptional).isPresent();
        assertThat(bueroRaumOptional.get().getName()).isEqualTo("Office#5");
    }

    @Test
    void cannotfindById() {
        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(0);
        assertThat(bueroRaumOptional).isNotPresent();
    }

    @Test
    void createNew() {
        final BueroRaum bueroRaum = bueroRaumEntityService.create();
        assertThat(bueroRaum).isNotNull();
        assertThat(bueroRaum.getId()).isPositive();

        final BueroRaum bueroRaum2 = bueroRaumEntityService.create();
        assertThat(bueroRaum.getId()).isLessThan(bueroRaum2.getId());
    }

    @Test
    void save() {
        final BueroRaum bueroRaum = bueroRaumEntityService.create();
        final String roomNumber = "#" + bueroRaum.getId();
        final String name = "Test Room " + roomNumber;
        bueroRaum.setRaumnummer(roomNumber);
        bueroRaum.setName(name);
        bueroRaum.setHoehe(2.8);
        bueroRaum.setFlaeche(25.4);
        bueroRaum.setKapazitaet(2);

        bueroRaumEntityService.save(bueroRaum);

        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(bueroRaum.getId());
        assertThat(bueroRaumOptional).isPresent();
        assertThat(bueroRaumOptional.get().getName()).isEqualTo(name);
        assertThat(bueroRaumOptional.get().getRaumnummer()).isEqualTo(roomNumber);
        assertThat(bueroRaumOptional.get().getHoehe()).isEqualTo(2.8);
        assertThat(bueroRaumOptional.get().getFlaeche()).isEqualTo(25.4);
        assertThat(bueroRaumOptional.get().getKapazitaet()).isEqualTo(2);
    }

    @Test
    void delete() {
        final BueroRaum bueroRaum = bueroRaumEntityService.create();
        final String roomNumber = "#" + bueroRaum.getId();
        final String name = "Test Room " + roomNumber;
        bueroRaum.setRaumnummer(roomNumber);
        bueroRaum.setName(name);
        bueroRaum.setHoehe(2.8);
        bueroRaum.setFlaeche(25.4);
        bueroRaum.setKapazitaet(2);

        bueroRaumEntityService.save(bueroRaum);
        bueroRaumEntityService.delete(bueroRaum);

        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(bueroRaum.getId());
        assertThat(bueroRaumOptional).isNotPresent();
    }

    @Test
    void createFrom() {
        final BueroDto bueroRaum = new BueroDto();
        final String roomNumber = "#17";
        final String name = "Test Room " + roomNumber;
        bueroRaum.setRaumnummer(roomNumber);
        bueroRaum.setName(name);
        bueroRaum.setKapazitaet(2);

        final BueroRaum from = bueroRaumEntityService.createFrom(bueroRaum);

        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(from.getId());
        assertThat(bueroRaumOptional).isPresent();
        assertThat(bueroRaumOptional.get().getName()).isEqualTo(name);
        assertThat(bueroRaumOptional.get().getRaumnummer()).isEqualTo(roomNumber);
        assertThat(bueroRaumOptional.get().getKapazitaet()).isEqualTo(2);
        // TODO alle Attribute prüfen
    }

    @Test
    void update() {
        final Optional<BueroRaum> bueroRaumOptional = bueroRaumEntityService.findById(11);
        final String name = "" + System.currentTimeMillis();
        bueroRaumOptional.ifPresent(b -> {
            b.setName(name);
            bueroRaumEntityService.update(b);
        });
        final Optional<BueroRaum> reloadedOffice = bueroRaumEntityService.findById(11);
        assertThat(reloadedOffice).isPresent();
        assertThat(reloadedOffice.get().getName()).isEqualTo(name);
    }
}