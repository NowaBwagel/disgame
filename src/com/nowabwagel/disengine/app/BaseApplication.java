package com.nowabwagel.disengine.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.nowabwagel.disengine.app.entitysystem.DefaultEntityData;
import com.nowabwagel.disengine.app.entitysystem.EntitySystem;
import com.nowabwagel.disengine.app.stateengine.AppStateManager;

public class BaseApplication implements ApplicationListener {

	protected InputMultiplexer inputMultiplexer;
	protected AssetManager assetManager;
	protected AppStateManager stateManager;
	protected EntitySystem entitySystem;

	private int width;
	private int height;

	public BaseApplication() {
		inputMultiplexer = new InputMultiplexer();
		assetManager = new AssetManager();
		stateManager = new AppStateManager(this);
		entitySystem = new EntitySystem(new DefaultEntityData());
	}

	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public AppStateManager getAppStateManager() {
		return stateManager;
	}

	public EntitySystem getEntitySystem() {
		return entitySystem;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		Gdx.gl.glCullFace(GL20.GL_BACK);
		Gdx.gl.glFrontFace(GL20.GL_CCW);
		Gdx.gl.glClearColor(0.13f, 0.13f, 0.13f, 1f);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stateManager.update(Gdx.graphics.getDeltaTime());

		stateManager.render();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void resume() {

	}

}
