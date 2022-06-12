package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Base class for all entity services (DAOs).
 *
 * @param <E> the entity class.
 */
@Slf4j
@Service
public abstract class AbstractEntityService<E extends Entity> extends DatabaseClient {

    @Autowired
    protected IdGenerator idGenerator;

    /**
     * @return an empty entity, initialized with a new, unique
     */
    public abstract E create();

    /**
     * Save the entity to the database
     */
    public abstract void save(E entity);

    public abstract List<E> findAll();

    public abstract Optional<E> findById(final long id);
}
