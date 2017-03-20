package com.nowabwagel.disengine.app.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.utils.Disposable;
import com.nowabwagel.disengine.app.BaseApplication;

public class AppStateManager {

	private final List<AppState> initializing = Collections.synchronizedList(new ArrayList<>());
	private final List<AppState> states = Collections.synchronizedList(new ArrayList<>());
	private final List<AppState> terminating = Collections.synchronizedList(new ArrayList<>());

	private final BaseApplication app;

	public AppStateManager(BaseApplication app) {
		this.app = app;
	}

	public BaseApplication getApplication() {
		return app;
	}

	protected AppState[] getInitializing() {
		synchronized (states) {
			return (AppState[]) initializing.toArray();
		}
	}

	protected AppState[] getStates() {
		synchronized (states) {
			return (AppState[]) states.toArray();
		}
	}

	protected AppState[] getTerminating() {
		synchronized (states) {
			return (AppState[]) terminating.toArray();
		}
	}

	public boolean attach(AppState state) {
		synchronized (states) {
			if (!states.contains(state) && !initializing.contains(state)) {
				state.onStateAttached(this);
				initializing.add(state);
				return true;
			} else {
				return false;
			}
		}
	}

	public void attachAll(AppState... states) {
		attachAll(Arrays.asList(states));
	}

	public void attachAll(Iterable<AppState> states) {
		synchronized (this.states) {
			for (AppState state : states) {
				attach(state);
			}
		}
	}

	public boolean detach(AppState state) {
		synchronized (states) {
			if (states.contains(state)) {
				state.onStateDetached(this);
				states.remove(state);
				terminating.add(state);
				return true;
			} else if (initializing.contains(state)) {
				state.onStateDetached(this);
				initializing.remove(state);
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean hasState(AppState state) {
		synchronized (states) {
			return states.contains(state) || initializing.contains(state);
		}
	}

	public <T extends AppState> T getState(Class<T> stateClass) {
		synchronized (states) {
			AppState[] raw = getStates();
			for (AppState state : raw) {
				if (stateClass.isAssignableFrom(state.getClass())) {
					return (T) state;
				}
			}

			raw = getInitializing();
			for (AppState state : raw) {
				if (stateClass.isAssignableFrom(state.getClass())) {
					return (T) state;
				}
			}
		}
		return null;
	}

	protected void initializePending(){
		AppState[] raw = getInitializing();
		if(raw.length == 0){
			return;
		}
		
		synchronized(states){
			List<AppState> bucket = Arrays.asList(raw);
			states.addAll(bucket);
			initializing.removeAll(bucket);
		}
		for(AppState state: raw){
			state.initialize(this, app);
		}
	}
}
