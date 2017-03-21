package com.nowabwagel.disengine.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.nowabwagel.disengine.app.state.AppStateManager;

public class BaseApplication implements ApplicationListener {

	protected InputMultiplexer inputMultiplexer;
	protected AssetManager assetManager;
	protected AppStateManager stateManager;

	private int width;
	private int height;

	public BaseApplication() {
		inputMultiplexer = new InputMultiplexer();
		assetManager = new AssetManager();
		stateManager = new AppStateManager(this);
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
