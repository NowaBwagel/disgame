/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowabwagel.disengine.entitysystem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author Noah Bergl
 */
public class DefaultEntityData implements EntityData {

    private ConcurrentMap<Class, ConcurrentMap<EntityId, ? extends Component>> componentMaps;
    private EntityIdGenerator idGenerator;

    public DefaultEntityData() {
        componentMaps = new ConcurrentHashMap<Class, ConcurrentMap<EntityId, ? extends Component>>();
        idGenerator = new EntityIdGenerator();
    }

    public EntityId newEntity() {
        return new EntityId(idGenerator.getNextId());
    }

    public void removeEntity(EntityId entity) {
        for (ConcurrentMap<EntityId, ? extends Component> componentMap : componentMaps.values()) {
            componentMap.remove(entity);
        }
    }

    public void addComponent(EntityId entity, Component component) {
        ConcurrentMap<EntityId, ? extends Component> componentMap = componentMaps.get(component.getClass());

        // IF map does not exist, lets make it
        if (componentMap == null) {
            componentMap = new ConcurrentHashMap();
            componentMaps.put(component.getClass(), componentMap);
        }
        ((ConcurrentMap<EntityId, Component>) componentMap).put(entity, component);
    }

    public <T extends Component> void removeComponent(EntityId entity, Class<T> componentClass) {
        ConcurrentMap<EntityId, ? extends Component> componentMap = componentMaps.get(componentClass);

        if (componentMap != null) {
            ((ConcurrentMap<EntityId, T>) componentMap).remove(entity);
        }
    }

    public <T extends Component> T getComponent(EntityId entity, Class<T> componentClass) {
        ConcurrentMap<EntityId, ? extends Component> componentMap = componentMaps.get(componentClass);

        if (componentMap == null) {
            componentMap = new ConcurrentHashMap();
            componentMaps.put(componentClass, componentMap);
            return null;
        }
        return ((ConcurrentMap<EntityId, T>) componentMap).get(entity);
    }

    public <T extends Component> boolean hasComponent(EntityId entity, Class<T> componentClass) {
        ConcurrentMap<EntityId, ? extends Component> componentMap = componentMaps.get(componentClass);

        if (componentMap == null) {
            componentMap = new ConcurrentHashMap();
            componentMaps.put(componentClass, componentMap);
            return false;
        }

        return ((ConcurrentMap<EntityId, T>) componentMap).containsKey(entity);
    }

    public Set<EntityId> getAllEntityWithComponents(Class<? extends Component>... components) {
        ConcurrentMap<EntityId, ? extends Component> componentMap = componentMaps.get(components[0]);
        Set<EntityId> set = new HashSet();

        if (componentMap == null) {
            componentMap = new ConcurrentHashMap();
            componentMaps.put(components[0], componentMap);
            return set;
        }

        for (EntityId id : componentMap.keySet()) {
            set.add(id);
        }

        //Check if entity has remaining components

        Iterator<EntityId> iterator = set.iterator();
        EntityId value;
        while (iterator.hasNext()) {
            value = iterator.next();

            for (int i = 1; i < components.length; i++) {
                // Get next map for component to check if entity is in that map also
                componentMap = componentMaps.get(components[i]);
                if (componentMap.containsKey(value) == false) {
                    //Does not have component so remvoe from iterator which is the set
                    iterator.remove();
                    break;
                }
            }
        }

        return set;
    }
}
