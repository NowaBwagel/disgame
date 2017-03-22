package com.nowabwagel.disgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] args) {
		// TexturePacker.process("unpacked/", "assets/", "voxel-assets");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "disgame";
		config.width = 800;
		config.height = 600;
		config.samples = 1;
		config.vSyncEnabled = false;
		config.foregroundFPS = 300;
		config.backgroundFPS = 15;
		new LwjglApplication(new Disgame(), config);
	}
}
