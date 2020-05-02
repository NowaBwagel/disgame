package old.nowabwagel.openrpg.entity.components.builders;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.JsonValue;

import old.nowabwagel.openrpg.Assets;
import old.nowabwagel.openrpg.Physics;
import old.nowabwagel.openrpg.entity.components.CollisionComponent;

public class CollisionComponentBuilder extends ComponentBuilder<CollisionComponent> {

	@Override
	public CollisionComponent build(JsonValue componentdata) {
		btCollisionObject body = new btCollisionObject();
		Physics.CollisionTypes type;
		String collisionType = componentdata.getString("collision-type");

		switch (collisionType.toLowerCase()) {
		case "static":
			type = Physics.CollisionTypes.STATIC;
			Physics.applyStaticGeometryCollisionFlags(body);
			break;
		case "dynamic":
			type = Physics.CollisionTypes.DYNAMIC;
			Physics.applyDynamicEntityCollisionFlags(body);
			break;
		default:
			type = Physics.CollisionTypes.STATIC;
			break;
		}

		String bodyType = componentdata.getString("body-type");
		switch (collisionType) {
		case "mesh":
			String mesh = componentdata.getString("mesh");
			btCollisionShape shape = Bullet.obtainStaticNodeShape(Assets.manager.get(mesh, Model.class).nodes);
			body.setCollisionShape(shape);
			break;
		default:
			break;
		}

		CollisionComponent cc = new CollisionComponent(body, type);
		return cc;
	}

}
