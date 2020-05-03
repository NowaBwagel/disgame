package old.common.entity.builders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Matrix4;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.nowabwagel.openrpg.entity.components.WorldTransformComponent;

import old.common.entity.ComponentBuilder;

public class WorldTransformComponentBuilder extends ComponentBuilder<WorldTransformComponent> {

	public WorldTransformComponentBuilder(String tag) {
		super(tag);
	}

	@Override
	public WorldTransformComponent buildComponent(JsonObject templateData) {
		Matrix4 matrix = (Matrix4) templateData.get("matrix-values");
		WorldTransformComponent component = new WorldTransformComponent(matrix);
		return component;
	}

	@Override
	public void loadDependencies(AssetManager manager, JsonObject templateData) {

	}

}
