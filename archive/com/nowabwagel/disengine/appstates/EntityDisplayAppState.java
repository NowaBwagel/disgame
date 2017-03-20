/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowabwagel.disengine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.nowabwagel.disengine.components.InSceneComponent;
import com.nowabwagel.disengine.components.PositionComponent;
import com.nowabwagel.disengine.components.VisualRepComponent;
import com.nowabwagel.disengine.entitysystem.Entity;
import com.nowabwagel.disengine.entitysystem.EntitySet;
import com.nowabwagel.disengine.entitysystem.EntitySystem;
import com.nowabwagel.disengine.entitysystem.EntityVisualController;
import com.nowabwagel.disgame.Disgame;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Noah Bergl
 */
public class EntityDisplayAppState extends AbstractAppState {

    private EntitySet entitySet;
    private Map<Entity, EntityVisualController> controls;
    private AssetManager assetManager;
    private Node rootNode;

    public EntityDisplayAppState(Node node) {
        rootNode = node;
        rootNode.setShadowMode(ShadowMode.CastAndReceive);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        EntitySystem entitySystem = ((Disgame) app).getEntitySystem();
        this.assetManager = app.getAssetManager();

        controls = new HashMap<Entity, EntityVisualController>();

        entitySet = entitySystem.getEntitySet(InSceneComponent.class, PositionComponent.class, VisualRepComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (entitySet.updateSetChanges()) {
            add(entitySet.getAddedEntities());
            changed(entitySet.getChangedEntities());
            removed(entitySet.getRemovedEntities());
        }
    }

    private void add(Set<Entity> set) {
        Iterator<Entity> iterator = set.iterator();

        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            EntityVisualController evc = new EntityVisualController(entity, assetManager);
            controls.put(entity, evc);
            rootNode.attachChild(evc.getSpatial());
        }
    }

    private void changed(Set<Entity> set) {
        Iterator<Entity> iterator = set.iterator();

        while (iterator.hasNext()) {
            controls.get(iterator.next()).updateEntity();
        }
    }

    private void removed(Set<Entity> set) {
        Iterator<Entity> iterator = set.iterator();
        
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            EntityVisualController evc = controls.get(entity);
            evc.remove();
            rootNode.detachChild(evc.getSpatial());
            controls.remove(entity);
        }
    }
}
