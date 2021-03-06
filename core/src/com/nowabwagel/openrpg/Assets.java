package com.nowabwagel.openrpg;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Logger;

import old.common.entity.EntityFactory;
import old.common.entity.EntityTemplate;
import old.common.entity.EntityTemplateFileLoader;

public class Assets {
	public static Assets instance;

	private AssetManager manager;

	public Assets() {
		
		if (instance != null) {
			throw new IllegalArgumentException(
					"The Assets have already been initalized use the instance with, Assets.instance");
		}
		instance = this;

		manager = new AssetManager();
		manager.setErrorListener(new AssetErrorListener() {
			@Override
			public void error(AssetDescriptor assetDescriptor, Throwable throwable) {
				System.err.println(assetDescriptor.toString());
				System.err.println(throwable.getMessage());
			}
		});
		manager.getLogger().setLevel(Logger.DEBUG);
	}

	public AssetManager getAssetManager() {
		return manager;
	}
}
