package com.nowabwagel.dmtool;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nowabwagel.dmtool.widgets.MainMenu;

public class DMTool extends Game {

	private AssetManager assetManager;

	private BitmapFont font72;
	private BitmapFont font32;
	private BitmapFont font24;
	private BitmapFont font16;
	private BitmapFont font12;

	private Stage stage;
	private MainMenu mainMenuWidget;

	@Override
	public void create() {
		assetManager = new AssetManager();
		loadSkin();

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		mainMenuWidget = new MainMenu(assetManager);
		mainMenuWidget.setDebug(true, true);
		stage.addActor(mainMenuWidget);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		mainMenuWidget.layout();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		assetManager.dispose();
	}

	private void loadSkin() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/OpenSans-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font12 = generator.generateFont(parameter);
		parameter.size = 16;
		font16 = generator.generateFont(parameter);
		parameter.size = 24;
		font24 = generator.generateFont(parameter);
		parameter.size = 32;
		font32 = generator.generateFont(parameter);
		parameter.size = 72;
		font72 = generator.generateFont(parameter);
		generator.dispose();

		ObjectMap<String, Object> fontMap = new ObjectMap<String, Object>();
		fontMap.put("font-72", font72);
		fontMap.put("font-32", font32);
		fontMap.put("font-24", font24);
		fontMap.put("font-16", font16);
		fontMap.put("font-12", font12);
		
		SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter(fontMap);
		
		assetManager.load("data/skin.json", Skin.class, skinParameter);
		assetManager.finishLoading();
	}
}
