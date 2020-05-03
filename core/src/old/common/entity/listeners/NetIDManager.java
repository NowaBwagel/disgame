package old.common.entity.listeners;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import old.common.entity.components.NetworkComponent;

public class NetIDManager implements EntityListener {
	Map<Integer, Entity> entityMap;
	Map<Entity, Integer> idMap;

	ComponentMapper<NetworkComponent> nm;

	public NetIDManager() {
		this.entityMap = new HashMap<>();
		this.idMap = new HashMap<>();
		this.nm = ComponentMapper.getFor(NetworkComponent.class);
	}

	@Override
	public void entityAdded(Entity entity) {
		NetworkComponent netComp = nm.get(entity);
		entityMap.put(new Integer(netComp.getID()), entity);
		idMap.put(entity, new Integer(netComp.getID()));
	}

	@Override
	public void entityRemoved(Entity entity) {
		NetworkComponent netComp = nm.get(entity);
		entityMap.remove(new Integer(netComp.getID()));
		idMap.remove(entity);
	}

	public Entity findEntityByID(NetworkComponent netComp) {
		return entityMap.get(netComp.getID());
	}

	public Entity findEntityByID(int id) {
		return entityMap.get(id);
	}
}
