package com.nowabwagel.disengine.entitysystem;

/**
 *
 * @author Noah Bergl
 */
// Every Entity has an EntityEvent, the EntityEvent will monitor is there is change to
// the Entity.
public class EntityEvent {

    private Entity entity;
    private EntityId entityId;
    private Component component;
    private EventType eventType;

    public enum EventType {

        Add, Change, Remove;
    }

    public EntityEvent(Entity entity, Component component, EventType eventType) {
        this.entity = entity;
        this.component = component;
        this.eventType = eventType;
        this.entityId = entity.getId();
    }

    public EntityEvent(EntityId entityId, Component component, EventType eventType) {
        this.entity = null;
        this.component = component;
        this.eventType = eventType;
        this.entityId = entityId;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityId getEntityId() {
        return entityId;
    }

    public <T extends Component> T getComponent() {
        return (T) component;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Class getComponentClass() {
        return component.getClass();
    }
}
