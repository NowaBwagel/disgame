package old.common.entity.systems;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.nowabwagel.openrpg.entity.components.WorldTransformComponent;

import old.common.entity.components.ChunkActivityFlagComponent;
import old.common.entity.components.ChunkComponent;

//TODO: Create IntervalIteratingSystem Class, and change to it so other implementations can be made.
public class EntitySleepSystem extends IteratingSystem implements EntityListener {

	private int chunkSize;

	private Array<Integer> activeChunks;
	private Array<Entity> awaitingSleepCheck;
	private Map<Integer, Array<Entity>> sleepingEntities;

	ComponentMapper<WorldTransformComponent> wtcm = ComponentMapper.getFor(WorldTransformComponent.class);
	ComponentMapper<ChunkComponent> ccm = ComponentMapper.getFor(ChunkComponent.class);
	ComponentMapper<ChunkActivityFlagComponent> cam = ComponentMapper.getFor(ChunkActivityFlagComponent.class);

	private Vector3 tmpEntityAddedVector = new Vector3();

	public EntitySleepSystem(int chunkSize) {
		super(Family.all(WorldTransformComponent.class, ChunkComponent.class, ChunkActivityFlagComponent.class).get());

		this.chunkSize = chunkSize;
		activeChunks = new Array<>();
		awaitingSleepCheck = new Array<>();
		sleepingEntities = new HashMap<>();
	}

	float accumulator = 0;

	@Override
	public void update(float deltaTime) {
		this.accumulator += deltaTime;
		if (this.accumulator >= 0.2) {
			this.accumulator = 0;
			super.update(deltaTime);

			for (Entity e : awaitingSleepCheck) {
				ChunkComponent cc = ccm.get(e);
				if (!activeChunks.contains(cc.getEncoded(), true)) {
					Array<Entity> sleeping = (sleepingEntities.containsKey(cc.getEncoded()))
							? sleepingEntities.get(cc.getEncoded())
							: new Array<Entity>();

					sleeping.add(e);
				}
			}
		}
	}

	/**
	 * Check if the entity moved, if it did then update ChunkComponent. Check if
	 * chunk the entity is in is active or sleeping. If sleeping check if Chunk
	 * Flagged, then remove from engine then move to sleeping.
	 * 
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		WorldTransformComponent wtc = wtcm.get(entity);
		ChunkActivityFlagComponent cac = cam.get(entity);
		ChunkComponent cc = ccm.get(entity);
		int oldEncoded = cc.getEncoded();

		int x = (int) (wtc.getTransform().getTranslation(tmpEntityAddedVector).x) % chunkSize;
		int y = (int) (wtc.getTransform().getTranslation(tmpEntityAddedVector).y) % chunkSize;
		cc.set(x, y);

		int newEncoded = cc.getEncoded();
		if (cac.isEnabled()) {
			if (sleepingEntities.containsKey(newEncoded)) {
				Array<Entity> entities = sleepingEntities.get(newEncoded);
				for (Entity e : entities) {
					this.getEngine().addEntity(e);
				}
				sleepingEntities.remove(newEncoded);
			}
		} else if (oldEncoded != newEncoded) {
			awaitingSleepCheck.add(entity);
		}
	}

	@Override
	public void entityAdded(Entity entity) {
		WorldTransformComponent wtc = wtcm.get(entity);
		int x = (int) (wtc.getTransform().getTranslation(tmpEntityAddedVector).x) % chunkSize;
		int y = (int) (wtc.getTransform().getTranslation(tmpEntityAddedVector).y) % chunkSize;
		ChunkComponent cc = new ChunkComponent(x, y);
		entity.add(cc);
	}

	/**
	 * Should never be removed, but if it is then it would never get deactivate.
	 */
	@Override
	public void entityRemoved(Entity entity) {
		entity.remove(ChunkComponent.class);
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addEntityListener(Family.all(WorldTransformComponent.class).get(), this);
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeEntityListener(this);
	}
}
