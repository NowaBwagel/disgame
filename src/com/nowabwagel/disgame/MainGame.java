package com.nowabwagel.disgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader.ObjLoaderParameters;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.nowabwagel.disgame.terrain.Terrain;

public class MainGame extends Game {

	SpriteBatch spriteBatch;
	BitmapFont font;
	ModelBatch modelBatch;
	PerspectiveCamera camera;
	Environment env;
	FirstPersonCameraController controller;
	Terrain terrain;

	Model model;
	ModelInstance instance;

	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		modelBatch = new ModelBatch();

		Gdx.gl.glCullFace(GL20.GL_BACK);
		Gdx.gl.glFrontFace(GL20.GL_CCW);

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.1f;
		camera.far = 300f;
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
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
		env.set(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1f));
		env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		terrain = new Terrain(10, 10);

		camera.position.set(0, 15, 0);
		camera.direction.set(0.64f, -0.4f, 0.7f);
		camera.update();

		ModelLoader loader = new ObjLoader();
		model = loader.loadModel(Gdx.files.internal("assets/tree.obj"), new ObjLoaderParameters(true));
		instance = new ModelInstance(model);
		instance.materials.get(0).set(new TextureAttribute(
				TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("assets/tree.png")))));
		// instance.transform.scale
	}

	String indices = "";
	String vertices = "";

	@Override
	public void render() {
		controller.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.13f, 0.13f, 0.13f, 1);

		modelBatch.begin(camera);
		modelBatch.render(terrain, env);
		modelBatch.render(instance, env);
		modelBatch.end();

		spriteBatch.begin();
		font.draw(spriteBatch, "fps: " + Gdx.graphics.getFramesPerSecond() + ", #visible tiles: "
				+ terrain.renderedTiles + "/" + terrain.numTiles, 0, 20);
		spriteBatch.end();
	}
}
