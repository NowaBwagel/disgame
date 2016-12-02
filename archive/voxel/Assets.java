package com.nowabwagel.voxel;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

	private static AssetManager manager;
	public static TextureAtlas textures;

	public static void create() {
		manager = new AssetManager();
		manager.load("assets/voxel-assets.atlas", TextureAtlas.class);
	}

	public static boolean update() {
		if (manager.update()) {
			done();
			return true;
		}
		return false;
	}

	public static void done() {
		textures = manager.get("assets/voxel-assets.atlas", TextureAtlas.class);
	}

	public static void dispose() {
		manager.dispose();
	}
}
