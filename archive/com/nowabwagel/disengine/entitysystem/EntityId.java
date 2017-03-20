package com.nowabwagel.disengine.entitysystem;

/**
 *
 * @author Noah Bergl
 */
public class EntityId {

    private long id;
    public static EntityId emptyId = new EntityId(Long.MIN_VALUE);

    public EntityId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
