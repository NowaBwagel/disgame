package old.common.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import old.common.entity.builders.ModelInstanceComponentBuilder;
import old.common.entity.builders.WorldTransformComponentBuilder;

public class EntityFactory {

	//private JsonReader json = new JsonReader();
	//private JSONParser jsonParser = new JSONParser();

	/**
	 * String : Entity ID Key
	 */
	private Map<String, EntityTemplate> templates;
	/**
	 * String : Component Tag
	 */
	private Map<String, ComponentBuilder<? extends Component>> builders;

	public EntityFactory() {
		this.templates = new HashMap<>();
		this.builders = new HashMap<>();

		// TODO: move ComponentTags to separate config file.
		builders.put("WorldTransformComponent", new WorldTransformComponentBuilder("WorldTransformComponent"));
		builders.put("ModelInstanceComponent", new ModelInstanceComponentBuilder("ModelInstanceComponent"));
	}

	public ComponentBuilder getBuilder(String tag) {
		return builders.get(tag);
	}

	public void registerTemplate(String ID, EntityTemplate template) {
		//Gdx.app.debug("EntityFactory", "Registering EntityTemplate: " + ID);
		this.templates.put(ID, template);
	}

	public Map<String, ComponentBuilder<? extends Component>> getComponentBuilders() {
		return builders;
	}

	public boolean registerComponentBuilder(String tag, ComponentBuilder<? extends Component> builder) {
		if (builders.containsKey(tag))
			return false;
		builders.put(tag, builder);
		return true;
	}

	public void setTemplates(Map<String, EntityTemplate> templates) {
		this.templates = templates;
	}

	/**
	 * Create Entity from EntityID. EntityID should be defined in
	 * EntityJsonTemplates.
	 * 
	 * @param ID
	 * @return Can return null.
	 */
	public Entity create(String ID) {
		//Gdx.app.debug("EntityFactory:Create", "Factory: " + this);
		//Gdx.app.debug("EntityFactory:Create", "Creating Entity From EntityTemplate ID: " + ID);
		//Gdx.app.debug("EntityFactory:Create", "Registed EntityTemplates Count: " + templates.size());

		EntityTemplate template = templates.get(ID);

		Entity entity = new Entity();
		if (template != null) {
			for (String tag : template.getTemplateTags()) {
				ComponentBuilder builder = builders.get(tag);
				if (builder == null) {
					Gdx.app.error("EntityFactory:Create", "Failed to find ComponentBuilder for: " + tag);
					continue;
				}

				Component comp = builder.buildComponent(template);
				entity.add(comp);
			}
		} else {
			Gdx.app.error("EntityFactory:Create", "Failed to find EntityTemplate: " + ID);
		}

		return entity;
	}

	/**
	 * Convenience for chaining
	 * 
	 * @param jsonObject
	 * @return
	 */
	public Entity attachComponents(Entity entity, JsonObject jsonObject) {
		//System.out.println(jsonObject.toJSONString());
		JsonArray array = (JsonArray) jsonObject.get("components");
		Iterator<Object> iterator = array.iterator();

		while (iterator.hasNext()) {
			JsonObject component = (JsonObject) iterator.next();
			String componentTag = (String) component.get("tag");
			//Gdx.app.debug("EntityFactory:attachComponentes:Building", "Building: " + componentTag);

			ComponentBuilder builder = builders.get(componentTag);
			if (builder == null) {
				Gdx.app.error("EntityFactory:AttachComponents", "Failed to find ComponentBuilder for: " + componentTag);
				continue;
			}

			Component comp = builder.buildComponent(component);
			entity.add(comp);
		}

		return entity;
	}

}
