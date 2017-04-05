package com.nowabwagel.disengine.app.entitysystem;

import java.util.Set;

/**
 *
 * @author Noah Bergl
 */
public interface EntityData {

    public EntityId newEntity();

    public void removeEntity(EntityId entity);

    public void addComponent(EntityId entity, DataComponent component);

    public <T extends DataComponent> void removeComponent(EntityId entity, Class<T> componentClass);

    public <T extends DataComponent> T getComponent(EntityId entity, Class<T> componentClass);

    public <T extends DataComponent> boolean hasComponent(EntityId entity, Class<T> componentClass);

    public Set<EntityId> getAllEntityWithComponents(Class<? extends DataComponent>... components);
}
