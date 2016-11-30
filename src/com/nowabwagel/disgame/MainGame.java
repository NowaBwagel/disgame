package com.nowabwagel.disgame;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.nowabwagel.disgame.terrain.TerrainTile;
import com.nowabwagel.disgame.terrain.WorldTerrain;

public class MainGame extends Game {

	SpriteBatch spriteBatch;
	BitmapFont font;
	ModelBatch modelBatch;
	PerspectiveCamera camera;
	Environment env;
	FirstPersonCameraController controller;
	WorldTerrain terrain;

	Model cube;
	Model cube2;
	ArrayList<ModelInstance> cubeInstances;

	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		modelBatch = new ModelBatch();

		// Gdx.gl.glCullFace(GL20.GL_BACK);
		// Gdx.gl.glFrontFace(GL20.GL_CW);

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.5f;
		camera.far = 1000;
		controller = new FirstPersonCameraController(camera) {

			// @Override
			// public boolean keyTyped(char character) {
			// if (terrain.indOffset < TerrainTile.VERTEX_COUNT *
			// TerrainTile.VERTEX_COUNT - 1)
			// terrain.indOffset++;
			// else
			// terrain.indOffset = 0;
			// return super.keyTyped(character);
			// }

		};
		Gdx.input.setInputProcessor(controller);

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 0.5f));
		env.add(new DirectionalLight().set(1, 1, 1, 0, -1, 0));

		terrain = new WorldTerrain(1, 1);

		camera.position.set(0, 15, 0);
		camera.direction.set(0.64f, -0.4f, 0.7f);
		camera.update();

		cubeInstances = new ArrayList<ModelInstance>();

		// Corret Place
		ModelBuilder modelBuilder = new ModelBuilder();
		cube = modelBuilder.createBox(0.5f, 0.5f, 0.5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position | Usage.Normal);

		cube2 = modelBuilder.createBox(0.5f, 0.5f, 0.5f, new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				Usage.Position | Usage.Normal);

		float[] pos = terrain.vertices;
		int pointer = 0;
		for (int gz = 0; gz < TerrainTile.VERTEX_COUNT; gz++) {
			for (int gx = 0; gx < TerrainTile.VERTEX_COUNT; gx++) {
				ModelInstance instance = new ModelInstance(cube2);
				instance.materials.get(0)
						.set(new Material(ColorAttribute.createDiffuse(
								(float) pointer / (TerrainTile.VERTEX_COUNT * TerrainTile.VERTEX_COUNT),
								1 - (float) pointer / (TerrainTile.VERTEX_COUNT * TerrainTile.VERTEX_COUNT), 1, 1)));
				instance.transform.setTranslation(pos[pointer * 3], 0, pos[pointer * 3 + 2]);
				cubeInstances.add(instance);
				pointer++;
			}

		}

		for (int i = 0; i < terrain.indices.length; i += 6) {
			indices += "[" + terrain.indices[i] + terrain.indices[i + 1] + terrain.indices[i + 2]
					+ terrain.indices[i + 3] + terrain.indices[i + 4] + terrain.indices[i + 5] + "]";
		}

		// for (int i = 0; i < terrain.vertices.length; i++) {
		// vertices += terrain.vertices[i] + ", ";
		// }
	}

	String indices = "";
	String vertices = "";

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(camera);
		modelBatch.render(terrain, env);
		modelBatch.render(cubeInstances, env);
		modelBatch.end();
		controller.update();

		spriteBatch.begin();
		// font.draw(spriteBatch, "fps: " + Gdx.graphics.getFramesPerSecond() +
		// ", #visible tiles: "
		// + terrain.renderedTiles + "/" + terrain.numTiles + " indices: " +
		// indices, 0, 20);
		font.draw(spriteBatch, "vertices: " + vertices + " indices: " + indices, 0, 50);
		spriteBatch.end();
	}
}
