package old.nowabwagel.openrpg.entity.entitylisteners;

import java.util.Map;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import old.nowabwagel.openrpg.entity.components.NetworkComponent;

public class NetIDManager implements EntityListener {

	Map<Integer, Entity> entityMap;
	Map<Entity, Integer> idMap;

	ComponentMapper<NetworkComponent> im;

	@Override
	public void entityAdded(Entity entity) {
		NetworkComponent netID = im.get(entity);
		entityMap.put(new Integer(netID.ID), entity);
		idMap.put(entity, new Integer(netID.ID));
	}

	@Override
	public void entityRemoved(Entity entity) {
		NetworkComponent netID = im.get(entity);
		entityMap.remove(new Integer(netID.ID));
		idMap.remove(entity);
	}

	public Entity findEntityByID(NetworkComponent netID) {
		return entityMap.get(netID.ID);
	}
}
