package old.nowabwagel.openrpg.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Matrix4;

import old.nowabwagel.openrpg.Assets;
import old.nowabwagel.openrpg.entity.components.NetworkComponent;
import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.networking.PacketType;
import old.nowabwagel.openrpg.server.entitysystems.EntityTransformReplicatorSystem;
import old.nowabwagel.openrpg.server.packetexecutors.LoginExecutor;
import old.nowabwagel.openrpg.world.World;

public class OpenRPGServer extends ApplicationAdapter {

	// Assets asset = new Assets();
	Thread packetServerThead;
	PacketServer packetServer;

	Engine engine;
	// World world;

	@Override
	public void create() {
		super.create();

		packetServer = new PacketServer();
		packetServerThead = new Thread(packetServer);
		packetServerThead.start();

		engine = new Engine();
		// world = new World(engine, null);

		packetServer.packetManager.attachPacketExecutor(PacketType.LOGIN, new LoginExecutor());

		engine.addSystem(new ClientManagerSystem());
		engine.addSystem(new EntityTransformReplicatorSystem());

		for (int i = 0; i < 20; i++) {
			engine.addEntity(getEntity());
		}
	}

	@Override
	public void render() {
		engine.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public static void main(String[] args) {
		HeadlessApplicationConfiguration cfg = new HeadlessApplicationConfiguration();
		cfg.renderInterval = 1 / 300f;
		new HeadlessApplication(new OpenRPGServer(), cfg);
	}

	private int lastID = 0;

	private Entity getEntity() {
		Entity entity = new Entity();
		NetworkComponent net = new NetworkComponent(++lastID);
		TransformComponent tra = new TransformComponent();
		float x = (float) (Math.random() * 15);
		float y = (float) (Math.random() * 15);
		float z = (float) (Math.random() * 15);
		tra.transform = new Matrix4().translate(x, y, z);
		return entity;
	}
}
