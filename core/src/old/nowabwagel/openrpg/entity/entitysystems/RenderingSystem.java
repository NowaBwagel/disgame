package old.nowabwagel.openrpg.entity.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;

import old.nowabwagel.openrpg.entity.components.VisualComponent;

public class RenderingSystem extends IteratingSystem {

	private ModelBatch modelBatch;
	private Array<Entity> renderQueue;
	private PerspectiveCamera cam;

	private ComponentMapper<VisualComponent> mm;

	public RenderingSystem(ModelBatch modelBatch, PerspectiveCamera cam) {
		super(Family.all(VisualComponent.class).get(), 1);
		this.modelBatch = modelBatch;
		this.cam = cam;
		renderQueue = new Array<Entity>();

		mm = ComponentMapper.getFor(VisualComponent.class);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		cam.update();
		modelBatch.begin(cam);
		for (Entity entity : renderQueue) {
			modelBatch.render(mm.get(entity).modelInstance);
		}
		modelBatch.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}

}
