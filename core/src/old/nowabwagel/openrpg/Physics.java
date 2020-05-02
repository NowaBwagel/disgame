package old.nowabwagel.openrpg;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import old.nowabwagel.openrpg.entity.components.CollisionComponent;

public class Physics implements EntityListener, Disposable {

	public enum CollisionTypes {
		STATIC, DYNAMIC
	}

	public static Physics inst;
	btCollisionConfiguration collisionConfig;
	btDispatcher dispatcher;
	MyContactListener contactListener;
	btBroadphaseInterface broadphaseInterface;
	private btCollisionWorld world;
	public Array<btCollisionShape> shapes;
	public Array<btCollisionObject> objects;

	private Array<Disposable> disposables;

	DebugDrawer debugDrawer;

	public Physics() {
		Bullet.init();
		shapes = new Array<>();
		objects = new Array<>();
		disposables = new Array<>();
		// TODO: Enable DebugDrawer on clients
		/*
		 * debugDrawer - new DebugDrawer();
		 * debugDrawer.setDebugMode(bt.DebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE)
		 * ;
		 */
		inst = this;
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphaseInterface = new btDbvtBroadphase();
		world = new btCollisionWorld(dispatcher, broadphaseInterface, collisionConfig);
		// TODO:Check if client
		/*
		 * world.setDebugDrawer(debugDrawer);
		 */
		contactListener = new MyContactListener();

		disposables.add(world);
		if (debugDrawer != null) {
			disposables.add(debugDrawer);
		}
		disposables.add(collisionConfig);
		disposables.add(dispatcher);
		disposables.add(broadphaseInterface);
		disposables.add(contactListener);
	}

	// Collision filters
	public static final short STATIC_GEOMETRY = 0x01;
	public static final short DYNAMIC_ENTITIES = 0x02;
	public static final short ALL_OBJECTS = 0xFF;

	public void addStaticGeometryToWorld(btCollisionObject obj) {
		short colGroup = STATIC_GEOMETRY;
		short colMask = DYNAMIC_ENTITIES;
		world.addCollisionObject(obj, colGroup, colMask);
	}

	public void addDynamicGeometryToWorld(btCollisionObject obj) {
		short colGroup = DYNAMIC_ENTITIES;
		short colMask = DYNAMIC_ENTITIES | STATIC_GEOMETRY;
		world.addCollisionObject(obj, colGroup, colMask);
	}

	public static void applyStaticGeometryCollisionFlags(btCollisionObject obj) {
		obj.setCollisionFlags(CollisionFlags.CF_STATIC_OBJECT);
	}

	public static void applyDynamicEntityCollisionFlags(btCollisionObject obj) {
		obj.setCollisionFlags(CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK); // Will allow ContactListeners to be called
																			// for dynamic objects.
	}

	class MyContactListener extends ContactListener {

	}

	ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);

	@Override
	public void entityAdded(Entity entity) {
		CollisionComponent cc = cm.get(entity);
		disposables.add(cc.body);
		disposables.add(cc.body.getCollisionShape());

		switch (cc.collisionType) {
		case DYNAMIC:
			this.addDynamicGeometryToWorld(cc.body);
			break;
		case STATIC:
			this.addStaticGeometryToWorld(cc.body);
			break;
		default:
			break;

		}
	}

	@Override
	public void entityRemoved(Entity entity) {
		CollisionComponent cc = cm.get(entity);

		world.removeCollisionObject(cc.body);
	}

	@Override
	public void dispose() {
		for (Disposable d : disposables)
			d.dispose();
	}
}
