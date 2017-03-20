package com.nowabwagel.disengine.entitysystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Noah Bergl
 */
public class EntitySystem {
    
    private List<EntityEventListener> entityChangeListeners;
    private EntityData entityData;
    
    public EntitySystem(EntityData entityData) {
        this.entityData = entityData;
        // To make sure the list is thread-safe
        entityChangeListeners = new CopyOnWriteArrayList<EntityEventListener>();
    }
    
    public void addEntityChangeListener(EntityEventListener changeListener) {
        entityChangeListeners.add(changeListener);
    }
    
    public void removeEntityChangeListener(EntityEventListener changeListener) {
        entityChangeListeners.remove(changeListener);
    }
    
    public void newEntity(Component... components) {
        EntityId id = entityData.newEntity();
        EntityEvent events[] = new EntityEvent[components.length];
        
        for (int i = 0; i < components.length; i++) {
            entityData.addComponent(id, components[i]);
            events[i] = new EntityEvent(id, components[i], EntityEvent.EventType.Add);
        }
        
        for (EntityEventListener listener : this.entityChangeListeners) {
            if(fitsToListener(id, listener)){
                for(int i = 0; i < components.length; i++){
                    listener.receiveEntityEvent(events[i]);
                }
            }
        }
    }
    // Check if an entity has components the listener needs.

    private boolean fitsToListener(EntityId entityId, EntityEventListener listener) {
        Class<? extends Component>[] intrestedIn = listener.componentsIntrestedIn();
        
        for (int i = 0; i < intrestedIn.length; i++) {
            if (entityData.hasComponent(entityId, intrestedIn[i]) == false) {
                return false;
            }
        }
        
        return true;
    }
    
    public void removeEntity(Entity entity) {
        entityData.removeEntity(entity.getId());
    }
    
    public void processEntityEvent(EntityEvent event) {
        if (event.getEventType() == EntityEvent.EventType.Remove) {
            for (int i = 0; i < entityChangeListeners.size(); i++) {
                if (fitsToListener(event.getEntityId(), entityChangeListeners.get(i))) {
                    entityChangeListeners.get(i).receiveEntityEvent(event);
                }
            }
            
            entityData.removeComponent(event.getEntityId(), event.getComponentClass());
            
        }else{
            entityData.addComponent(event.getEntityId(), event.getComponent());
            
            for(int i = 0; i < entityChangeListeners.size(); i++){
                if(fitsToListener(event.getEntityId(), entityChangeListeners.get(i))){
                    entityChangeListeners.get(i).receiveEntityEvent(event);
                }
            }
        }
        
    }
    
    public EntitySet getEntitySet(Class<? extends Component> ... componentClasses){
        
        Map<EntityId, Entity> entityMap = new HashMap<EntityId, Entity>();
        
        EntitySet entitySet = new EntitySet();
        
        Set set = entityData.getAllEntityWithComponents(componentClasses);
        Iterator<EntityId> iterator = set.iterator();
        while(iterator.hasNext()){
            EntityId id = iterator.next();
            Component components[] = new Component[componentClasses.length];
            
            for(int i = 0; i < componentClasses.length;i++){
                components[i] = entityData.getComponent(id, componentClasses[i]);
            }
            
            Entity entity = new Entity(id, components, componentClasses, entitySet);
            entityMap.put(id, entity);
        }
        
        entitySet.init(this, entityMap, componentClasses);
        return entitySet;
    }
    
}
