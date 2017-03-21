package com.nowabwagel.disengine.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.IntIntMap;
import com.nowabwagel.disengine.app.state.AppStateManager;
import com.nowabwagel.disengine.app.state.InputProcessingAppState;

public class FlyCamAppState extends InputProcessingAppState {
	private BaseApplication app;
	private Camera cam;

	private IntIntMap keys;
	private float velocity = 5;
	private float degreePerPixel = 0.5f;

	public FlyCamAppState() {
		this.keys = new IntIntMap();
	}

	public FlyCamAppState(Camera cam) {
		this.cam = cam;
		this.keys = new IntIntMap();
	}

	public void setCamera(Camera cam) {
		this.cam = cam;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public void setDegreePerPixel(float degreePP) {
		this.degreePerPixel = degreePP;
	}

	@Override
	public boolean keyDown(int keycode) {
		keys.put(keycode, keycode);
		// FIXME: should not consume all keys
		// Should not be a problem as long as at bottom of InputMultiplexer
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		keys.remove(keycode, 0);
		return super.keyUp(keycode);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public void initialize(AppStateManager manager, BaseApplication application) {
		super.initialize(manager, application);

		this.app = application;

		if (cam == null) {
			Gdx.app.error("FlyCamAppState", "initialized w/o camera set -> use setCamera()");
			this.setEnable(false);
		}
	}

	@Override
	public void update(float tpf) {

		cam.update(true);
	}

}
