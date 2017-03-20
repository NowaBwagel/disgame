package com.nowabwagel.disgame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.nowabwagel.disengine.appstates.CubeMovementAppState;
import com.nowabwagel.disengine.appstates.EntityDisplayAppState;
import com.nowabwagel.disengine.entitysystem.DefaultEntityData;
import com.nowabwagel.disengine.entitysystem.EntitySystem;

/**
 *
 * @author Noah Bergl
 */
public class Disgame extends SimpleApplication {

    private EntitySystem entitySystem;
    private TerrainQuad terrain;

    public static void main(String[] args) {
        Disgame game = new Disgame();
        game.start();
    }

    @Override
    public void update() {
        super.update();
        //System.out.println(cam.getLocation());
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(0f, 5f, 0f));

        entitySystem = new EntitySystem(new DefaultEntityData());

        EntityFactory.init(entitySystem);

        this.stateManager.attach(new EntityDisplayAppState(this.rootNode));
        this.stateManager.attach(new CubeMovementAppState());


        EntityFactory.createTree(new Vector3f(15, 0, 15), Quaternion.ZERO);
        //EntityFactory.createTree(Vector3f.ZERO, Quaternion.ZERO);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);

        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
        Texture terrainTexture = assetManager.loadTexture("Textures/TerrainTexture.png");
        terrainTexture.setWrap(Texture.WrapMode.Repeat);

        Material terrainMat = assetManager.loadMaterial("Materials/TerrainTest.j3m");
        terrainMat.setFloat("Tex1Scale", 4f);
        // Material terrainMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        //terrainMat.setTexture("DiffuseMap", terrainTexture);

        //terrainMat.setBoolean("UseMaterialColors", true);
        //terrainMat.setColor("Ambient", ColorRGBA.Green);
        //terrainMat.setColor("Diffuse", ColorRGBA.Green);
        //terrainMat.getAdditionalRenderState().setWireframe(true);
        
        flyCam.setEnabled(true);

        AbstractHeightMap heightMap = null;
        Texture heightMapImage = assetManager.loadTexture("Textures/TerrainTestFlat.png");
        heightMap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightMap.load();
        heightMap.erodeTerrain();

        int patchSize = 129;
        terrain = new TerrainQuad("my terrain", patchSize, 513, heightMap.getHeightMap());

        terrain.setMaterial(terrainMat);
        terrain.setLocalTranslation(0, 0, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        terrain.setShadowMode(ShadowMode.Receive);

        rootNode.attachChild(terrain);

        TerrainLodControl terrainControl = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(terrainControl);


        EntityFactory.createPlayer(Vector3f.ZERO, Quaternion.ZERO);
    }

    public EntitySystem getEntitySystem() {
        return entitySystem;
    }
}
