package old.common.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.assets.AssetManager;
import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Used for storing known Component Template Data (ie, what data per id). Used
 * to build a Component from id, if in known Template.
 * 
 * @author noahb
 *
 * @param <T>
 */
public abstract class ComponentBuilder<T extends Component> {
	String componentTag;

	public ComponentBuilder(String componentTag) {
		this.componentTag = componentTag;
	}

	public String getComponentTag() {
		return componentTag;
	}

	/**
	 * Try to build a component from a template
	 * 
	 * @param template
	 *            EntityTemplate to try to build.
	 * @return
	 */
	public T buildComponent(EntityTemplate template) {
		return buildComponent(template.getTemplateData(componentTag));
	}

	/**
	 * Make a component from template
	 * 
	 * @param templateData
	 * @return Component
	 */
	public abstract T buildComponent(JsonObject templateData);

	/**
	 * Read TemplateData during loading to add all dependent assets to assetmanager
	 * to load.
	 * 
	 * @param manager
	 * @param templateData
	 */
	public abstract void loadDependencies(AssetManager manager, JsonObject templateData);
}