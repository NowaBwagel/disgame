package old.nowabwagel.openrpg.server.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.jmr.wrapper.common.Connection;

import old.nowabwagel.openrpg.entity.components.NetworkComponent;
import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.entity.entitylisteners.NetIDManager;
import old.nowabwagel.openrpg.networking.PacketWrapper;
import old.nowabwagel.openrpg.networking.packets.EntityUpdatePacket;
import old.nowabwagel.openrpg.server.ClientManagerSystem;

public class EntityTransformReplicatorSystem extends IntervalSystem {

	ComponentMapper<NetworkComponent> nm = ComponentMapper.getFor(NetworkComponent.class);
	ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	ImmutableArray<Entity> entities;

	public EntityTransformReplicatorSystem() {
		super(0.1f); // 10 per sec
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(TransformComponent.class).get());
	}

	@Override
	protected void updateInterval() {
		for (Entity entity : entities) {
			NetworkComponent net = nm.get(entity);
			TransformComponent tran = tm.get(entity);

			EntityUpdatePacket packet = new EntityUpdatePacket();
			packet.netCom = net;
			packet.tranCom = tran;

			for (Connection con : ClientManagerSystem.connections) {
				System.out.println("replicating to: " + con.getAddress());
				con.sendUdp(new PacketWrapper(null, packet));
			}
		}
	}

}
