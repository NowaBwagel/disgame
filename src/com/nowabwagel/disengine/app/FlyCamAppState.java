package com.nowabwagel.disengine.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;
import com.nowabwagel.disengine.app.state.AppStateManager;
import com.nowabwagel.disengine.app.state.InputProcessingAppState;

public class FlyCamAppState extends InputProcessingAppState {
	private BaseApplication app;
	private Camera camera;
	private IntIntMap keys;
	private int STRAFE_LEFT = Keys.A;
	private int STRAFE_RIGHT = Keys.D;
	private int FORWARD = Keys.W;
	private int BACKWARD = Keys.S;
	private int UP = Keys.Q;
	private int DOWN = Keys.E;
	private float velocity = 5;
	private float degreePerPixel = 0.5f;
	private final Vector3 tmp = new Vector3();

	public FlyCamAppState() {
		this.keys = new IntIntMap();
	}

	public FlyCamAppState(Camera cam) {
		this.camera = cam;
		this.keys = new IntIntMap();
	}

	public void setCamera(Camera cam) {
		this.camera = cam;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public void setDegreePerPixel(float degreePP) {
		this.degreePerPixel = degreePP;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float deltaX = -Gdx.input.getDeltaX() * degreePerPixel;
		float deltaY = -Gdx.input.getDeltaY() * degreePerPixel;
		camera.direction.rotate(camera.up, deltaX);
		tmp.set(camera.direction).crs(camera.up).nor();
		return true;
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
		return true;
	}

	@Override
	public void initialize(AppStateManager manager, BaseApplication application) {
		super.initialize(manager, application);

		this.app = application;

		if (camera == null) {
			Gdx.app.error("FlyCamAppState", "initialized w/o camera set -> use setCamera()");
			this.setEnable(false);
		}
	}

	@Override
	public void update(float tpf) {
		if (keys.containsKey(FORWARD)) {
			tmp.set(camera.direction).nor().scl(tpf * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(BACKWARD)) {
			tmp.set(camera.direction).nor().scl(-tpf * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(STRAFE_LEFT)) {
			tmp.set(camera.direction).crs(camera.up).nor().scl(-tpf * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(STRAFE_RIGHT)) {
			tmp.set(camera.direction).crs(camera.up).nor().scl(tpf * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(UP)) {
			tmp.set(camera.up).nor().scl(tpf * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(DOWN)) {
			tmp.set(camera.up).nor().scl(-tpf * velocity);
			camera.position.add(tmp);
		}
		camera.update(true);
	}

}