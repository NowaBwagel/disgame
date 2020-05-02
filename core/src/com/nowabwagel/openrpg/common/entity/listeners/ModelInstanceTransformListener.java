package com.nowabwagel.openrpg.common.entity.listeners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.nowabwagel.openrpg.common.entity.components.ModelInstanceComponent;
import com.nowabwagel.openrpg.common.entity.components.WorldTransformComponent;

//FIXME: Unit test of if better as listener or a system that updates model every frame not cloning transform.
public class ModelInstanceTransformListener implements EntityListener {

	ComponentMapper<ModelInstanceComponent> mm = ComponentMapper.getFor(ModelInstanceComponent.class);
	ComponentMapper<WorldTransformComponent> tm = ComponentMapper.getFor(WorldTransformComponent.class);

	@Override
	public void entityAdded(Entity entity) {
		ModelInstanceComponent mc = mm.get(entity);
		WorldTransformComponent tc = tm.get(entity);

		mc.getModelInstance().transform = tc.getTransform();

	}

	@Override
	public void entityRemoved(Entity entity) {
		// TODO Auto-generated method stub

	}

}
