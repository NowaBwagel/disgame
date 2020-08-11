package com.nowabwagel.dmtool;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Globals {
    
    public static final AssetManager assetManager = new AssetManager();
    public static Skin skin;

    public static void loadSkin() {

	assetManager.load("data/skin.json", MySkin.class);
	assetManager.finishLoading();
	skin = assetManager.get("data/skin.json");
    }
}
