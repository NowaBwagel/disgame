package com.nowabwagel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.nowabwagel.game.MainGame;

public class DesktopLauncher {
	public static void main(String[] args) {
		TexturePacker.process("unpacked/", "assets/", "voxel-assets");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Voxel Game";
		config.width = 800;
		config.height = 600;
		config.samples = 1;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 15;
		new LwjglApplication(new MainGame(), config);
	}
}
