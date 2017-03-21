package com.nowabwagel.disengine.app.state;

import com.nowabwagel.disengine.app.BaseApplication;

public interface AppState {

	public void initialize(AppStateManager manager, BaseApplication application);

	public boolean isInitialized();

	public void setEnable(boolean active);

	public boolean isEnabled();

	public void onStateAttached(AppStateManager manager);

	public void onStateDetached(AppStateManager manager);

	public void onStateTerminated();

	public void update(float tpf);

	public void render();

	public void postRender();

}
