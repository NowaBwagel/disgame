package com.nowabwagel.disengine.app.state;

import com.nowabwagel.disengine.app.BaseApplication;

public class AbstractAppState implements AppState {
	
	protected boolean initialized = false;
	private boolean enabled = true;

	@Override
	public void initialize(AppStateManager manager, BaseApplication application) {
		initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void setEnable(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void onStateAttached(AppStateManager manager) {
		
	}

	@Override
	public void onStateDetached(AppStateManager manager) {
		
	}

	@Override
	public void onStateTerminated() {
		initialized = false;
		
	}

	@Override
	public void update(float tpf) {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void postRender() {
		
	}

}
