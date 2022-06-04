package de.htw_berlin.imi.db.entities;

/**
 * Base class for all entities.
 */
public abstract class Entity {
    private final long id;

    protected Entity(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
