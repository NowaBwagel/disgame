package com.nowabwagel.openrpg.client.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.nowabwagel.openrpg.client.ClientOpenRPGWorld;
import com.nowabwagel.openrpg.client.FirstPersonCameraController;
import com.nowabwagel.openrpg.client.entity.systems.RenderingSystem;
import com.nowabwagel.openrpg.client.networking.ClientNetworkWrapper;
import com.nowabwagel.openrpg.common.GameConfig;
import com.nowabwagel.openrpg.common.entity.EntityFactory;
import com.nowabwagel.openrpg.common.entity.components.ModelInstanceComponent;
import com.nowabwagel.openrpg.common.entity.components.NetworkComponent;
import com.nowabwagel.openrpg.common.entity.components.WorldTransformComponent;
import com.nowabwagel.openrpg.common.entity.listeners.ModelInstanceTransformListener;
import com.nowabwagel.openrpg.common.entity.listeners.NetIDManager;
import com.nowabwagel.openrpg.common.networking.packets.EntitySnapshotPacket;
import com.nowabwagel.openrpg.common.networking.packets.RequestEntityUpdatePacket;
import com.nowabwagel.openrpg.common.networking.packets.RequestWorldPacket;

public class GameScreen implements Screen {

	//private ClientNetworkWrapper clientNetworkWrapper;
	private Engine engine;
	private EntityFactory entityFactory;

	//private NetIDManager netIDManager;
	private ModelBatch modelBatch;
	private PerspectiveCamera camera;

	//TODO: Got nuked with networking
	//private ClientOpenRPGWorld openRPGWorld;

	public GameScreen(ClientNetworkWrapper clientNetworkWrapper, EntityFactory entityFactory, Engine engine) {
		//this.clientNetworkWrapper = clientNetworkWrapper;
		this.entityFactory = entityFactory;
		this.engine = engine;
		// TODO: move to reset method.
		engine.removeAllEntities();
		//netIDManager = new NetIDManager();

		modelBatch = new ModelBatch();
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(-2f, 3f, -2f);
		camera.lookAt(2, 3, 2);
		camera.near = 1f;
		camera.far = 300f;
		camera.update();


		engine.addEntityListener(Family.all(ModelInstanceComponent.class, WorldTransformComponent.class).get(),
				new ModelInstanceTransformListener());
		engine.addEntityListener(Family.all(NetworkComponent.class).get(), new NetIDManager());
		engine.addSystem(new RenderingSystem(modelBatch, camera));

	}

	@Override
	public void show() {
		//this.clientNetworkWrapper.connect(GameConfig.loginHOST, GameConfig.loginTCP, GameConfig.loginUDP);
		//this.clientNetworkWrapper.getPacketManager().resetManager();

		//openRPGWorld = new ClientOpenRPGWorld(netIDManager, entityFactory, engine);
		//clientNetworkWrapper.attachPacketExecutor(EntitySnapshotPacket.class, openRPGWorld);
		//clientNetworkWrapper.getServerConnection().sendTcp(new RequestWorldPacket());

	}

	@Override
	public void render(float delta) {
		engine.update(delta);

		// TODO: Render UI.
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
