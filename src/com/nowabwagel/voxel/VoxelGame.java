package com.nowabwagel.voxel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.nowabwagel.voxel.screens.GameScreen;
import com.nowabwagel.voxel.screens.LoadingScreen;

public class VoxelGame extends Game {

	Screen titleScreen;

	@Override
	public void create() {
		// TODO: Make Title screen
		titleScreen = new GameScreen(this);

		setScreen(new LoadingScreen(this));

	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
	}

	public Screen getTitleScreen() {
		return titleScreen;
	}

}
