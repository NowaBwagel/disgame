package com.nowabwagel.openrpg.common.entity;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class EntityTemplateFileLoader
		extends AsynchronousAssetLoader<EntityTemplate, EntityTemplateFileLoader.EntityTemplateParameter> {

	private EntityFactory factory;

	public EntityTemplateFileLoader(FileHandleResolver resolver, EntityFactory factory) {
		super(resolver);
		this.factory = factory;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, EntityTemplateParameter parameter) {

	}


	/**
	 * Will load EntityTemplateData into each given ComponentBuilder
	 */
	@Override
	public EntityTemplate loadSync(AssetManager manager, String fileName, FileHandle file,
			EntityTemplateParameter parameter) {
		try {
			new JsonObject();
			JsonObject root = (JsonObject) Jsoner.deserialize(file.reader());
			String ID = (String) root.get("id");
			JsonArray components = (JsonArray) root.get("components");
			Iterator<Object> iterator = components.iterator();

			EntityTemplate template = new EntityTemplate(ID);
			while (iterator.hasNext()) {
				JsonObject component = (JsonObject) iterator.next();
				String compTag = (String) component.get("tag");
				template.setTemplateData(compTag, component);
				factory.getBuilder(compTag).loadDependencies(manager, component);
			}

			Gdx.app.log("EntityTemplateFileLoader", "Registering EntityTemplate: " + ID);
			factory.registerTemplate(ID, template);

			return template;
		} catch (JsonException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, EntityTemplateParameter parameter) {
		return null;
	}

	public class EntityTemplateParameter extends AssetLoaderParameters<EntityTemplate> {
	}
}
