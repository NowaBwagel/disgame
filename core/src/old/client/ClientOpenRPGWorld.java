package old.client;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.jmr.wrapper.common.Connection;

import old.common.entity.EntityFactory;
import old.common.entity.EntitySnapshot;
import old.common.entity.EntitySnapshotBundle;
import old.common.entity.listeners.NetIDManager;
import old.common.networking.IPacketExecutor;
import old.common.networking.Packet;
import old.common.networking.packets.EntitySnapshotPacket;
import old.common.world.AbstractOpenRPGWorld;

public class ClientOpenRPGWorld extends AbstractOpenRPGWorld implements IPacketExecutor {

	private NetIDManager netIDManager;
	private Engine engine;
	private EntityFactory entityFactory;

	public ClientOpenRPGWorld(NetIDManager netIDManager, EntityFactory entityFactory, Engine engine) {
		this.netIDManager = netIDManager;
		this.engine = engine;
		this.entityFactory = entityFactory;

	}

	boolean loaded = false;

	@Override
	public void execute(Connection sender, Packet packet) {
		if (packet instanceof EntitySnapshotPacket) {
			EntitySnapshotPacket ePacket = (EntitySnapshotPacket) packet;
			EntitySnapshotBundle bundle = ePacket.bundle;
			Gdx.app.debug("ClientOpenRPGWorld:execute:EntityAddPacket", "Bundle size: " + bundle.getSnapshots().size());

			for (EntitySnapshot snapshot : bundle.getSnapshots()) {
				switch (snapshot.snapshotType) {
				case Full:
					Entity old = netIDManager.findEntityByID(snapshot.entityID);
					if (old != null) {
						engine.removeEntity(old);
					}

					Entity entity = entityFactory.create(snapshot.entityType);
					entityFactory.attachComponents(entity, snapshot.components);
					engine.addEntity(entity);
					loaded = true;
					break;
				case Update:
					if (!loaded)
						break;

					Entity entity1 = netIDManager.findEntityByID(snapshot.entityID);
					if (entity1 != null) {
						entityFactory.attachComponents(entity1, snapshot.components);
					} else {
						Gdx.app.error("ClientOpenRPGWorld:Execute:EntityAddPacket:Update",
								"Entity With NetworkID Not Found: " + snapshot.entityID);
					}
					break;
				default:
					break;
				}
			}

		}

	}

}
