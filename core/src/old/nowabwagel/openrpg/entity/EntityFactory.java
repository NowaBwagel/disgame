package old.nowabwagel.openrpg.entity;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.entity.components.builders.CollisionComponentBuilder;
import old.nowabwagel.openrpg.entity.components.builders.ComponentBuilder;

public class EntityFactory {
	private static final Json JSON = new Json();
	// TODO: Entity Types should be just the name of the entity, so should scan for
	// all types and index them for easy access. And once an entity is created with
	// the type then save the loaded template.

	public static final EntityType ROCK01 = new EntityType("/Entities/Rock01.json");
	public static final Map<String, ComponentBuilder> componentBuilders = new HashMap<>();

	private static JsonReader json = new JsonReader();
	private static JsonValue jsonValue1;
	private static JsonValue jsonValue2;
	
	static {
		componentBuilders.put("collision", new CollisionComponentBuilder());
	}

	public static Entity loadEntity(EntitySnapshot snapshot) {
		EntityType type = snapshot.entityType;
		Entity entity = loadEntity(type);

		entity.add(snapshot.networkComponent);
		entity.add(snapshot.transformComponent);
		return entity;
	}
	
	public static Entity loadBlankEntity(EntityType type) {
		Entity entity = loadEntity(type);
		entity.add(new TransformComponent());
		return entity;
	}

	private static Entity loadEntity(EntityType type) {
		Entity entity = new Entity();
		String loadFile = type.loadFile;

		jsonValue1 = json.parse(Gdx.files.internal(loadFile));
		jsonValue2 = jsonValue1.get("components");
		for (JsonValue jsonValue3 : jsonValue2) {
			entity.add(componentBuilders.get(jsonValue3.getString("type")).build(jsonValue3));
		}

		return entity;
	}

	// TODO: ComponentBuilder
}
