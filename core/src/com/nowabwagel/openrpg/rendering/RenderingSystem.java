package com.nowabwagel.openrpg.rendering;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import com.nowabwagel.openrpg.app.FirstPersonCameraController;
import com.nowabwagel.openrpg.entity.components.RenderableProviderComponent;

public class RenderingSystem extends IteratingSystem {

	private Environment environment;

	private ModelBatch modelBatch;
	private Array<RenderableProvider> renderQueue;
	private PerspectiveCamera camera;

	private ComponentMapper<RenderableProviderComponent> mm;

	private FirstPersonCameraController controller;

	// TODO:Look into ModelCache
	public RenderingSystem(ModelBatch modelBatch, PerspectiveCamera camera) {
		super(Family.all(RenderableProviderComponent.class).get(), 1);
		this.modelBatch = modelBatch;
		this.camera = camera;
		this.renderQueue = new Array<>();
		this.mm = ComponentMapper.getFor(RenderableProviderComponent.class);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);

		controller = new FirstPersonCameraController(camera);
		Gdx.input.setInputProcessor(controller);

	}

	@Override
	public void update(float delta) {
		renderQueue.clear();
		super.update(delta);
		controller.update();
		camera.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		modelBatch.render(renderQueue, environment);
		modelBatch.end();
	}

	/**
	 * Called when super.updated(); is called in update, for each entity.
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(mm.get(entity).provider);
	}

}
