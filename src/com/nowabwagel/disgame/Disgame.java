package com.nowabwagel.disgame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
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
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader.ObjLoaderParameters;
import com.nowabwagel.disengine.app.BaseApplication;
import com.nowabwagel.disengine.app.appstates.FlyCamAppState;

public class Disgame extends BaseApplication {

	private PerspectiveCamera camera;
	private FlyCamAppState flyCamState;

	ModelBatch batch;
	BitmapFont font;
	SpriteBatch spriteBatch;
	Environment env;
	Model model;
	ModelInstance instance;

	Model doggie;
	ModelInstance doggieInstance;

	List<ModelInstance> instances = new ArrayList<ModelInstance>();

	public Disgame() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		super.create();

		camera = new PerspectiveCamera(67f, this.getWidth(), this.getHeight());
		camera.near = 0.1f;
		camera.far = 1000f;

		camera.position.set(15, 3, 0);
		camera.update();

		flyCamState = new FlyCamAppState(camera);

		stateManager.attach(flyCamState);
		inputMultiplexer.addProcessor(flyCamState);

		batch = new ModelBatch();
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f));
		env.set(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1.0f));
		env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		ModelLoader loader = new ObjLoader();
		model = loader.loadModel(Gdx.files.internal("assets/tree.obj"), new ObjLoaderParameters(true));
		instance = new ModelInstance(model);
		instance.materials.get(0).set(new TextureAttribute(
				TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("assets/tree.png")))));

		assetManager.load("assets/Doggie.g3db", Model.class);

		assetManager.finishLoading();

		doggie = assetManager.get("assets/Doggie.g3db");
		doggieInstance = new ModelInstance(doggie);

		instances.add(instance);

		for (int x = 0; x < 20; x++) {
			for (int z = 0; z < 20; z++) {
				ModelInstance inst = new ModelInstance(doggie);
				inst.transform.setTranslation(x * 3, 0, z * 3);
				instances.add(inst);
			}
		}

	}

	float timer = 0;
	int fps = 0;
	int avFps = 0;

	List<Integer> fpss = new ArrayList<Integer>();

	@Override
	public void render() {
		super.render();

		timer += Gdx.graphics.getDeltaTime();

		batch.begin(camera);
		// batch.render(instance, env);
		batch.render(instances, env);
		batch.end();

		update();
		spriteBatch.begin();
		font.draw(spriteBatch, "fps: " + fps + " | average: " + avFps, 0, 20);
		spriteBatch.end();
	}

	void update() {
		fps = Gdx.graphics.getFramesPerSecond();
		if (timer >= 0.2) {
			fpss.add(new Integer(fps));

			if (fpss.size() >= 20) {
				fpss = fpss.subList(5, fpss.size() - 1);
			}

			avFps = 0;
			for (Integer i : fpss) {
				avFps += i.intValue();
			}
			avFps = avFps / fpss.size();

			timer = 0;
		}
	}

	@Override
	public void dispose() {
		super.dispose();

		model.dispose();
		batch.dispose();
		spriteBatch.dispose();
		font.dispose();
	}
}
