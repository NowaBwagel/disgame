/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowabwagel.disengine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.nowabwagel.disengine.components.PositionComponent;
import com.nowabwagel.disengine.components.TagComponent;
import com.nowabwagel.disengine.entitysystem.Entity;
import com.nowabwagel.disengine.entitysystem.EntitySet;
import com.nowabwagel.disengine.entitysystem.EntitySystem;
import com.nowabwagel.disgame.Disgame;
import java.util.Iterator;

/**
 *
 * @author Noah Bergl
 */
public class CubeMovementAppState extends AbstractAppState {

    private EntitySet entitySet;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        EntitySystem entitySystem = ((Disgame) app).getEntitySystem();

        entitySet = entitySystem.getEntitySet(TagComponent.class);
    }
    float passedTime = 0;

    @Override
    public void update(float tpf) {
        entitySet.updateSetChanges();
        
        passedTime += tpf;

        float pos = FastMath.sin(passedTime);
        Vector3f v3f = new Vector3f(0, pos, 0);

        Iterator<Entity> iterator = entitySet.getIterator();
        if (iterator == null) {
            return;
        }
        
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            PositionComponent pc = new PositionComponent(v3f, Quaternion.ZERO);
            entity.setComponent(pc);
        }
    }
}
