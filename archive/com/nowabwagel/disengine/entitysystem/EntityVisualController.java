/*
 * Every entity will have there own controller, because they will handle the animations for the entity.
 */
package com.nowabwagel.disengine.entitysystem;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.nowabwagel.disengine.components.InSceneComponent;
import com.nowabwagel.disengine.components.MovementComponent;
import com.nowabwagel.disengine.components.PositionComponent;
import com.nowabwagel.disengine.components.VisualRepComponent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Noah Bergl
 */
public final class EntityVisualController extends AbstractControl {

    private static final Logger logger = Logger.getLogger(EntityVisualController.class.getName());
    private Entity entity;
    private AssetManager assetManager;
    private String currentModelName;
    private Spatial currentModel;
    private List<AnimControl> animControls;
    private List<AnimChannel> animChannels;

    public EntityVisualController(Entity entity, AssetManager manager) {
        this.entity = entity;
        this.assetManager = manager;

        this.setSpatial(new Node());
        updateEntity();
    }

    public void updateEntity() {
        if (entity != null) {
            if (!updateVisualRep()) {
                return;
            }
            if (!updateLocation()) {
                return;
            }
            if (!updateAnimation()) {
                return;
            }
        }
    }

    public void remove() {
        entity = null;
        if (animControls != null) {
            animControls.clear();
        }
        if (animChannels != null) {
            animChannels.clear();
        }
    }

    private boolean updateVisualRep() {
        VisualRepComponent visComp = entity.getComponent(VisualRepComponent.class);

        if (visComp != null) {
            // Check if the model has changed
            if (currentModelName != null && currentModelName.equals(visComp.getAssetName())) {
                return true;
            } else {
                //Model changed, so kill the old one
                if (currentModel != null) {
                    setAnimControls(null);
                    currentModel.removeFromParent();
                }
                currentModelName = visComp.getAssetName();
                currentModel = assetManager.loadModel(currentModelName);
                setAnimControls(currentModel);
                ((Node) spatial).attachChild(currentModel);
            }
        } else {
            setAnimControls(null);
            spatial.removeFromParent();
            entity.removeComponent(InSceneComponent.class);
            return false;
        }
        return true;
    }

    private boolean updateLocation() {
        PositionComponent pos = entity.getComponent(PositionComponent.class);

        if (pos != null) {
            spatial.setLocalTranslation(pos.getLocation());
            spatial.setLocalRotation(pos.getRotation());
        }
        return true;
    }

    private boolean updateAnimation() {
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        //TODO: Work on animation system
        return true;
    }

    private void setAnimControls(Spatial spatial) {
        if (spatial == null) {
            if (animControls != null) {
                for (Iterator<AnimControl> it = animControls.iterator(); it.hasNext();) {
                    AnimControl animControl = it.next();
                    animControl.clearChannels();
                }
            }
            animControls = null;
            animChannels = null;
            return;
        }
        SceneGraphVisitorAdapter visitor = new SceneGraphVisitorAdapter() {
            @Override
            public void visit(Geometry geom) {
                super.visit(geom);
                checkForAnimControl(geom);
            }

            @Override
            public void visit(Node geom) {
                super.visit(geom);
                checkForAnimControl(geom);
            }

            private void checkForAnimControl(Spatial geom) {
                AnimControl control = geom.getControl(AnimControl.class);
                if (control == null) {
                    return;
                }
                if (animControls == null) {
                    animControls = new LinkedList<AnimControl>();
                }
                if (animChannels == null) {
                    animChannels = new LinkedList<AnimChannel>();
                }
                animControls.add(control);
                animChannels.add(control.createChannel());
            }
        };
        spatial.depthFirstTraversal(visitor);
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
