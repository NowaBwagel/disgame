package old.nowabwagel;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import old.nowabwagel.openrpg.Assets;
import old.nowabwagel.openrpg.Physics;
import old.nowabwagel.openrpg.entity.components.CollisionComponent;
import old.nowabwagel.openrpg.entity.components.NetworkComponent;
import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.entity.components.VisualComponent;
import old.nowabwagel.openrpg.entity.entitylisteners.NetIDManager;
import old.nowabwagel.openrpg.entity.entitylisteners.VisualTransformLinker;
import old.nowabwagel.openrpg.entity.entitysystems.RenderingSystem;
import old.nowabwagel.openrpg.networking.NetworkClientWrapper;
import old.nowabwagel.openrpg.networking.PacketType;
import old.nowabwagel.openrpg.world.World;

public class OpenRPGClient extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Assets assets;
	Physics physics;
	NetworkClientWrapper networkClient;
	Engine engine;
	NetIDManager netIDManager;

	PerspectiveCamera cam;
	ModelBatch modelBatch;
	World world;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		assets = new Assets();
		assets.manager.finishLoading();
		physics = new Physics();

		networkClient = new NetworkClientWrapper("localhost", 4395, 4395);

		engine = new Engine();
		netIDManager = new NetIDManager();
		engine.addEntityListener(Family.all(NetworkComponent.class).get(), netIDManager);
		engine.addEntityListener(Family.all(VisualComponent.class, TransformComponent.class).get(),
				new VisualTransformLinker());
		engine.addEntityListener(Family.all(CollisionComponent.class).get(), physics);

		cam = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		modelBatch = new ModelBatch();

		world = new World(engine, netIDManager);
		networkClient.attachPacketExecutor(PacketType.WORLD_SNAPSHOT, world);
		networkClient.attachPacketExecutor(PacketType.ENTITY_ADD, world);
		networkClient.attachPacketExecutor(PacketType.ENTITY_REMOVE, world);
		networkClient.attachPacketExecutor(PacketType.ENTITY_UPDATE, world);

		engine.addSystem(new RenderingSystem(modelBatch, cam));

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());

		// batch.begin();
		// batch.draw(img, 0, 0);
		// batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
