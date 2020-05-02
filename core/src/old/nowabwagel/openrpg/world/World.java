package old.nowabwagel.openrpg.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.jmr.wrapper.common.Connection;

import old.nowabwagel.openrpg.Assets;
import old.nowabwagel.openrpg.entity.EntityFactory;
import old.nowabwagel.openrpg.entity.components.NetworkComponent;
import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.entity.components.VisualComponent;
import old.nowabwagel.openrpg.entity.entitylisteners.NetIDManager;
import old.nowabwagel.openrpg.entity.entitysystems.MovementSystem;
import old.nowabwagel.openrpg.networking.IPacketExecutor;
import old.nowabwagel.openrpg.networking.Packet;
import old.nowabwagel.openrpg.networking.packets.EntityAddPacket;
import old.nowabwagel.openrpg.networking.packets.EntityUpdatePacket;

public class World implements IPacketExecutor {

	NetIDManager netManager;

	Engine engine;

	public World(Engine engine, NetIDManager netManager) {
		this.engine = engine;
		this.engine.addSystem(new MovementSystem());

		this.netManager = netManager;

		model = Assets.manager.get("Models/BigRock1.g3db", Model.class);
	}

	@Override
	public void execute(Connection sender, Packet packet) {
		if (packet instanceof EntityAddPacket) {
			// TODO: Possible networked entity duplication
			EntityAddPacket entityAddPacket = (EntityAddPacket) packet;
			Entity entity = EntityFactory.loadEntity(entityAddPacket.snapshot);

			engine.addEntity(entity);
		} else if (packet instanceof EntityUpdatePacket) {
			EntityUpdatePacket entityUpdatePacket = (EntityUpdatePacket) packet;

			Entity e;
			if ((e = netManager.findEntityByID(entityUpdatePacket.netCom)) != null) {
				TransformComponent current = e.getComponent(TransformComponent.class);
				current.transform.set(entityUpdatePacket.tranCom.transform);
			} else {
				spawnEntity(entityUpdatePacket.netCom, entityUpdatePacket.tranCom);
			}
		}
	}

	private Model model;

	private void spawnEntity(NetworkComponent net, TransformComponent tran) {
		Entity entity = new Entity();
		VisualComponent comp = new VisualComponent();
		comp.modelInstance = new ModelInstance(model);

		entity.add(net);
		entity.add(tran);
		entity.add(comp);
	}
}
