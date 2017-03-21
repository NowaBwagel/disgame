package com.nowabwagel.disgame;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.nowabwagel.disengine.app.BaseApplication;
import com.nowabwagel.disengine.app.FlyCamAppState;

public class Disgame extends BaseApplication {

	private PerspectiveCamera camera;
	private FlyCamAppState flyCamState;

	public Disgame() {

	}

	@Override
	public void create() {
		super.create();

		camera = new PerspectiveCamera(67f, this.getWidth(), this.getHeight());
		camera.near = 0.1f;
		camera.far = 1000f;

		flyCamState = new FlyCamAppState(camera);

		stateManager.attach(flyCamState);
	}
}
