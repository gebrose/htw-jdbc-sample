package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Raum;
import de.htw_berlin.imi.db.entities.Stockwerk;
import de.htw_berlin.imi.db.web.StockwerkDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockwerkEntityServiceTest extends AbstractEntityServiceTest {

    @Autowired
    StockwerkEntityService stockwerkEntityService;

    @Test
    void findAll() {
        final List<Stockwerk> all = stockwerkEntityService.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all)
                .extracting(Stockwerk::getId)
                .isNotEmpty();
    }

    @Test
    void findById() {
        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(1);
        assertThat(stockwerkOptional).isPresent();
        assertThat(stockwerkOptional.get().getGeschossnummer()).isEqualTo(1);
    }

    @Test
    void findByIdWithFetchedRooms() {
        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(6);
        assertThat(stockwerkOptional).isPresent();
        final List<Raum> raeume = stockwerkOptional.get().getRaeume();
        assertThat(raeume)
                .hasSize(6)
                .extracting(Raum::getName)
                .contains("Office#2", "Office#3", "Office#4", "Office#5");
    }

    @Test
    void cannotfindById() {
        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(0);
        assertThat(stockwerkOptional).isNotPresent();
    }

    @Test
    void createNew() {
        final Stockwerk stockwerk = stockwerkEntityService.create();
        assertThat(stockwerk).isNotNull();
        assertThat(stockwerk.getId()).isPositive();

        final Stockwerk stockwerk1 = stockwerkEntityService.create();
        assertThat(stockwerk.getId()).isLessThan(stockwerk1.getId());
    }

    @Test
    void save() {
        final Stockwerk stockwerk = stockwerkEntityService.create();
        stockwerk.setGeschossnummer(27);

        stockwerkEntityService.save(stockwerk);

        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(stockwerk.getId());
        assertThat(stockwerkOptional).isPresent();
        assertThat(stockwerkOptional.get().getGeschossnummer()).isEqualTo(27);
    }

    @Test
    void delete() {
        final Stockwerk stockwerk = stockwerkEntityService.create();
        stockwerk.setGeschossnummer(22222);

        stockwerkEntityService.save(stockwerk);
        stockwerkEntityService.delete(stockwerk);

        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(stockwerk.getId());
        assertThat(stockwerkOptional).isNotPresent();
    }

    @Test
    void createFrom() {
        final StockwerkDto stockwerkDto = new StockwerkDto();
        stockwerkDto.setGeschossnummer(47);

        final Stockwerk from = stockwerkEntityService.createFrom(stockwerkDto);

        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(from.getId());
        assertThat(stockwerkOptional).isPresent();
        assertThat(stockwerkOptional.get().getGeschossnummer()).isEqualTo(47);
    }

    @Test
    void update() {
        final Optional<Stockwerk> stockwerkOptional = stockwerkEntityService.findById(6);
        stockwerkOptional.ifPresent(b -> {
            b.setGeschossnummer(66);
            stockwerkEntityService.update(b);
        });
        final Optional<Stockwerk> reloadedStockwerk = stockwerkEntityService.findById(6);
        assertThat(reloadedStockwerk).isPresent();
        assertThat(reloadedStockwerk.get().getGeschossnummer()).isEqualTo(66);
    }
}