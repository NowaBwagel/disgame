package com.nowabwagel.voxel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.nowabwagel.voxel.Assets;
import com.nowabwagel.voxel.VoxelGame;

public class LoadingScreen implements Screen {

	private VoxelGame game;

	private float percent;

	public LoadingScreen(VoxelGame g) {
		this.game = g;

	}

	@Override
	public void show() {
		System.out.println("Showing LoadingScreen");
		Assets.create();
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		if (Assets.update()) {
			game.setScreen(game.getTitleScreen());
		}
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
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
