package de.htw_berlin.imi.db.entities;

/**
 * Base class for all entities.
 */
public abstract class Entity {

    /**
     * Unique Id as primary key.
     * </p>
     * To be generated from a database sequence, NOT chosen locally by the application.
     */
    private final long id;

    protected Entity(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
