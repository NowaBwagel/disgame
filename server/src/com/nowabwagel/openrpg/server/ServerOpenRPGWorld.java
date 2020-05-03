package com.nowabwagel.openrpg.server;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.jmr.wrapper.common.Connection;
import com.nowabwagel.openrpg.entity.components.WorldTransformComponent;
import com.nowabwagel.openrpg.server.entity.components.EntityTypeIDComponent;
import com.nowabwagel.openrpg.server.networking.ServerNetworkWrapper;

import old.common.entity.EntitySnapshot;
import old.common.entity.EntitySnapshotBundle;
import old.common.entity.EntitySnapshot.Type;
import old.common.entity.components.AbstractNetworkReplicatibleComponent;
import old.common.entity.components.NetworkComponent;
import old.common.networking.IPacketExecutor;
import old.common.networking.Packet;
import old.common.networking.packets.EntitySnapshotPacket;
import old.common.networking.packets.RequestEntityUpdatePacket;
import old.common.networking.packets.RequestWorldPacket;
import old.common.world.AbstractOpenRPGWorld;

public class ServerOpenRPGWorld extends AbstractOpenRPGWorld implements IPacketExecutor {

	// CURRENT WORKING ON FINISHING SERVER CODE FOR PRE ALPHA ENTITY / PACKET
	// SYSTEM, WITH ENTITY FACTORY CREATING ENTITY UPDATING AND ENTITY DELETEING.
	// WITH MULIPLE ENITYT TEMPLATES TO BE MADE

	Engine engine;

	ComponentMapper<NetworkComponent> nm = ComponentMapper.getFor(NetworkComponent.class);
	ComponentMapper<EntityTypeIDComponent> tm = ComponentMapper.getFor(EntityTypeIDComponent.class);

	List<Connection> players = new ArrayList<Connection>();

	public ServerOpenRPGWorld(ServerNetworkWrapper serverNetworkWrapper) {
		engine = new Engine();

		serverNetworkWrapper.attachPacketExecutor(RequestWorldPacket.class, this);
		serverNetworkWrapper.attachPacketExecutor(RequestEntityUpdatePacket.class, this);

		for (int i = 0; i < 100; i++) {
			Entity e = new Entity();
			NetworkComponent nc = new NetworkComponent(i);
			e.add(nc);
			EntityTypeIDComponent tc = new EntityTypeIDComponent("TestRock");
			e.add(tc);
			WorldTransformComponent pc = new WorldTransformComponent(new Matrix4());
			pc.getTransform().translate(MathUtils.random() * 100, MathUtils.random() * 5, MathUtils.random() * 100);
			pc.getTransform().rotate(0, 1, 0, MathUtils.random() * 360);
			e.add(pc);
			engine.addEntity(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Connection sender, Packet packet) {
		if (packet instanceof RequestWorldPacket) {
			Gdx.app.debug("ServerOpenRPGWorld:execute:RequestWorldPacket", "Building request.");
			EntitySnapshotBundle bundle = new EntitySnapshotBundle();
			int i = 0;
			for (Entity e : engine.getEntities()) {
				EntitySnapshot snapshot = new EntitySnapshot();
				snapshot.snapshotType = Type.Full;
				snapshot.entityID = nm.get(e).getID();
				snapshot.entityType = tm.get(e).getTypeID();
				JsonObject obj = new JsonObject();
				JsonArray array = new JsonArray();
				for (Component c : e.getComponents()) {
					if (c instanceof AbstractNetworkReplicatibleComponent) {
						AbstractNetworkReplicatibleComponent comp = (AbstractNetworkReplicatibleComponent) c;
						array.add(comp.getJSONObjectTagged());
					}
				}
				obj.put("components", array);
				snapshot.components = obj;
				bundle.addSnapshot(snapshot);
			}

			EntitySnapshotPacket responce = new EntitySnapshotPacket(bundle);
			Gdx.app.debug("ServerOpenRPGWorld:execute:RequestWorldPacket", "Sending Request.");
			sender.sendComplexObjectUdp(responce);
			players.add(sender);
		}
	}

	private void updateSender(Connection sender) {
		EntitySnapshotBundle bundle = new EntitySnapshotBundle();
		int i = 0;
		for (Entity e : engine.getEntities()) {
			EntitySnapshot snapshot = new EntitySnapshot();
			snapshot.snapshotType = Type.Update;
			snapshot.entityID = nm.get(e).getID();
			snapshot.entityType = tm.get(e).getTypeID();
			JsonObject obj = new JsonObject();
			JsonArray array = new JsonArray();
			for (Component c : e.getComponents()) {
				// TODO: Add check to see if packet changed.
				if (c instanceof AbstractNetworkReplicatibleComponent) {
					AbstractNetworkReplicatibleComponent comp = (AbstractNetworkReplicatibleComponent) c;
					array.add(comp.getJSONObjectTagged());
				}
			}
			obj.put("components", array);
			snapshot.components = obj;
			bundle.addSnapshot(snapshot);
		}

		EntitySnapshotPacket responce = new EntitySnapshotPacket(bundle);
		Gdx.app.debug("ServerOpenRPGWorld:execute:RequestEntityUpdatePacket", "Sending Request.");
		sender.sendComplexObjectUdp(responce);
	}
}
