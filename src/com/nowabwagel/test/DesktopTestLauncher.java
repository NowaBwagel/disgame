package com.nowabwagel.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopTestLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Testing Environment";
		config.width = 800;
		config.height = 600;
		config.samples = 1;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 15;
		new LwjglApplication(new TestArrayConnecter(), config);
	}
}
