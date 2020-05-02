package old.nowabwagel.openrpg.entity.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.entity.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	public MovementSystem() {
		super(Family.all(TransformComponent.class, VelocityComponent.class).get());
	}

	Vector3 v1 = new Vector3();
	Vector3 v2 = new Vector3();

	@Override
	protected void processEntity(Entity entity, float delta) {
		Matrix4 transform = tm.get(entity).transform;
		VelocityComponent velocity = vm.get(entity);

		// Set temp vector3 to velocity change
		v2.set(velocity.x, velocity.y, velocity.z).scl(delta);
		
		transform.getTranslation(v1);
		v1.add(v2);

		velocity.x = v1.x;
		velocity.y = v1.y;
		velocity.z = v1.z;
	}
}
