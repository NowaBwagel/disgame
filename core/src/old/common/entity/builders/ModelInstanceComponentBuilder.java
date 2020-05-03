package old.common.entity.builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.nowabwagel.openrpg.Assets;

import old.common.entity.ComponentBuilder;
import old.common.entity.components.ModelInstanceComponent;

public class ModelInstanceComponentBuilder extends ComponentBuilder<ModelInstanceComponent> {

	public ModelInstanceComponentBuilder(String tag) {
		super(tag);
	}

	@Override
	public ModelInstanceComponent buildComponent(JsonObject templateData) {
		Model model = Assets.instance.getAssetManager().get((String) templateData.get("file"), Model.class);
		// System.out.println("BoundingBox: " + model.calculateBoundingBox(new
		// BoundingBox()).getDimensions(new Vector3()));
		ModelInstanceComponent component = new ModelInstanceComponent(new ModelInstance(model));
		return component;
	}

	@Override
	public void loadDependencies(AssetManager manager, JsonObject templateData) {
		String file = (String) templateData.get("file");
		Gdx.app.debug("ModelInstanceComponentBuilder", "Loading Dependency: " + file);
		manager.load(file, Model.class);
	}

}
