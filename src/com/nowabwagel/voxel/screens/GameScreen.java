package com.nowabwagel.voxel.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.nowabwagel.voxel.VoxelGame;
import com.nowabwagel.voxel.world.World;
import com.nowabwagel.voxel.world.terrain.VoxelChunk;
import com.nowabwagel.voxel.world.voxels.Block;

public class GameScreen implements Screen {

	VoxelGame game;

	ModelBatch modelBatch;
	PerspectiveCamera camera;
	FirstPersonCameraController controller;

	SpriteBatch spriteBatch;
	BitmapFont font;

	World world;
	VoxelChunk chunk;

	public GameScreen(VoxelGame g) {
		this.game = g;
	}

	@Override
	public void show() {
		System.out.println("Showing GameScreen");
		Gdx.gl.glFrontFace(GL20.GL_CW);
		modelBatch = new ModelBatch();
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.1f;
		camera.far = 100f;
		camera.position.set(0, 0, 5);
		camera.lookAt(0, 0, 0);
		camera.update();
		controller = new FirstPersonCameraController(camera);
		Gdx.input.setInputProcessor(controller);

		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

		world = new World();
		world.set(0, 1, 8, Block.STONE);
		world.set(0, 2, 8, Block.STONE);
		world.set(0, 3, 8, Block.STONE);
		world.set(1, 2, 8, Block.STONE);
		world.set(2, 1, 8, Block.STONE);
		world.set(2, 2, 8, Block.STONE);
		world.set(2, 3, 8, Block.STONE);
		world.set(4, 1, 8, Block.STONE);
		world.set(4, 2, 8, Block.STONE);
		world.set(4, 4, 8, Block.STONE);

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++)
				world.set(x, 0, z, Block.TEST);
		}
		chunk = new VoxelChunk(0, 0, 0);
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		List<ModelInstance> worldModels = world.getWorldModels();

		modelBatch.begin(camera);
		modelBatch.render(worldModels, world.getEnvironment());
		modelBatch.end();

		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1, 12);
		spriteBatch.end();

		controller.update();
		world.update();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		spriteBatch.dispose();
		font.dispose();
		world.dispose();
	}

}
