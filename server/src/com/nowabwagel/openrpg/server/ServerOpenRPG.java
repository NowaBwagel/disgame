package com.nowabwagel.openrpg.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.nowabwagel.openrpg.common.AbstractOpenRPG;
import com.nowabwagel.openrpg.common.GameConfig;
import com.nowabwagel.openrpg.server.networking.ServerNetworkWrapper;

public class ServerOpenRPG extends AbstractOpenRPG {

	private ServerOpenRPGWorld openRPGWorld;

	public static void main(String[] args) {
		HeadlessApplicationConfiguration cfg = new HeadlessApplicationConfiguration();
		cfg.renderInterval = 1 / 300f;
		new HeadlessApplication(new ServerOpenRPG(), cfg);
	}

	private ServerNetworkWrapper serverNetworkWrapper;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		
		serverNetworkWrapper = new ServerNetworkWrapper();
		serverNetworkWrapper.connect(GameConfig.loginHOST, GameConfig.loginTCP, GameConfig.loginUDP);

		openRPGWorld = new ServerOpenRPGWorld(serverNetworkWrapper);

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void render() {
		serverNetworkWrapper.update();
		openRPGWorld.engine.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {

	}

}
