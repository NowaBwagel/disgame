package com.nowabwagel.dmtool;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nowabwagel.dmtool.widgets.MainMenu;

public class DMTool extends Game {

    private AssetManager assetManager;

    private Stage stage;
    private MainMenu mainMenuWidget;

    @Override
    public void create() {
	assetManager = Globals.assetManager;
	assetManager.setLoader(MySkin.class, new MySkinLoader(assetManager.getFileHandleResolver()));
	Globals.loadSkin();

	stage = new Stage(new ScreenViewport());
	Gdx.input.setInputProcessor(stage);

	mainMenuWidget = new MainMenu();
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
}
