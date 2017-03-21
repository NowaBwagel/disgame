package com.nowabwagel.disengine.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.nowabwagel.disengine.app.state.AppStateManager;

public class BaseApplication implements ApplicationListener {

	protected InputMultiplexer inputMultiplexer;
	protected AssetManager assetManager;
	protected AppStateManager stateManager;

	public BaseApplication() {
		inputMultiplexer = new InputMultiplexer();
	}

	@Override
	public void create() {

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

	}

	@Override
	public void resize(int arg0, int arg1) {

	}

	@Override
	public void resume() {

	}

}
