package old.nowabwagel.openrpg.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Disposable;

import old.nowabwagel.openrpg.Physics;

public class CollisionComponent implements Component, Disposable {

	public btCollisionObject body;
	public Physics.CollisionTypes collisionType;

	public CollisionComponent(btCollisionObject body, Physics.CollisionTypes type) {
		this.body = body;
		this.collisionType = type;
	}

	@Override
	public void dispose() {
		body.dispose();
	}
}
