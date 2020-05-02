package com.nowabwagel.openrpg.client;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.nowabwagel.openrpg.client.networking.ClientNetworkWrapper;
import com.nowabwagel.openrpg.client.screens.GameScreen;
import com.nowabwagel.openrpg.client.screens.LoginScreen;
import com.nowabwagel.openrpg.client.screens.PreLoginLoadingScreen;
import com.nowabwagel.openrpg.common.AbstractOpenRPG;
import com.nowabwagel.openrpg.common.Assets;
import com.nowabwagel.openrpg.common.entity.EntityFactory;
import com.nowabwagel.openrpg.common.entity.EntityTemplate;
import com.nowabwagel.openrpg.common.entity.EntityTemplateFileLoader;

public class ClientOpenRPG extends AbstractOpenRPG {

	ClientNetworkWrapper clientNetworkWrapper;

	Engine engine;
	EntityFactory entityFactory;
	Assets assets;
	// TODO: Physics

	// Screens
	PreLoginLoadingScreen preLoginLoadingScreen;
	LoginScreen loginScreen;
	GameScreen gameScreen;

	public ClientOpenRPG() {
		// TODO: Implement LoginServer / LoginScreen

		// JsonReader json = new JsonReader();
		// JsonValue root;
		// JsonValue componentMap;
		// FileHandle file = Gdx.files.internal("Entities/TestRock.json");
		// root = json.parse(file);
		// String ID = root.getString("id");
		// componentMap = root.get("components");
		// System.out.println("ID:" + ID);
		// for (JsonValue entry = componentMap.child; entry != null; entry = entry.next)
		// {
		// System.out.println("Component: " + entry.name);
		// System.out.println("File: " + entry.getString("file"));
		// System.out.println("Data: " + entry.getString("data"));
		// }
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

		clientNetworkWrapper = new ClientNetworkWrapper();

		engine = new Engine();
		entityFactory = new EntityFactory();

		assets = new Assets();
		assets.getAssetManager().setLoader(EntityTemplate.class,
				new EntityTemplateFileLoader(assets.getAssetManager().getFileHandleResolver(), entityFactory));
		assets.getAssetManager().load("Entities/TestRock.json", EntityTemplate.class);
		assets.getAssetManager().load("Textures/blocks/stone/stone_1.png", Texture.class);
		assets.getAssetManager().finishLoading();

		Gdx.app.debug("Assets", Assets.instance.getAssetManager().getAssetNames().toString());
		
		preLoginLoadingScreen = new PreLoginLoadingScreen();
		loginScreen = new LoginScreen(clientNetworkWrapper);
		gameScreen = new GameScreen(clientNetworkWrapper, entityFactory, engine);

		this.setScreen(gameScreen);

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
		super.render();
		clientNetworkWrapper.update();
	}

	@Override
	public void dispose() {
		assets.getAssetManager().dispose();
	}

}
