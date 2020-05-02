package old.nowabwagel.openrpg.entity.entitylisteners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.entity.components.VisualComponent;

public class VisualTransformLinker implements EntityListener {

	ComponentMapper<VisualComponent> vm = ComponentMapper.getFor(VisualComponent.class);
	ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);;

	@Override
	public void entityAdded(Entity entity) {
		VisualComponent visComp = vm.get(entity);
		TransformComponent tranComp = tm.get(entity);

		visComp.modelInstance.transform = tranComp.transform;
		visComp.linkedTransform = true;
	}

	@Override
	public void entityRemoved(Entity entity) {
		// We don't care if the transform / visual component are removed.
	}

}
