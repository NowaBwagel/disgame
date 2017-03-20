package com.nowabwagel.disengine.entitysystem;

import java.util.Set;

/**
 *
 * @author Noah Bergl
 */
public interface EntityData {

    public EntityId newEntity();

    public void removeEntity(EntityId entity);

    public void addComponent(EntityId entity, Component component);

    public <T extends Component> void removeComponent(EntityId entity, Class<T> componentClass);

    public <T extends Component> T getComponent(EntityId entity, Class<T> componentClass);

    public <T extends Component> boolean hasComponent(EntityId entity, Class<T> componentClass);

    public Set<EntityId> getAllEntityWithComponents(Class<? extends Component>... components);
}
