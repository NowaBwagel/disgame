package com.nowabwagel.disengine.app.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
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
			return toAppStateArray(initializing.toArray());
		}
	}

	protected AppState[] getStates() {
		synchronized (states) {
			return toAppStateArray(states.toArray());
		}
	}

	protected AppState[] getTerminating() {
		synchronized (states) {
			return toAppStateArray(terminating.toArray());
		}
	}

	private AppState[] toAppStateArray(Object[] input) {
		AppState[] export = new AppState[input.length];
		for (int i = 0; i < input.length; i++) {
			AppState tmp = (AppState) input[i];
			export[i] = tmp;
		}
		return export;
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public <T extends AppState> T[] getStates(Class<T> stateClass) {
		synchronized (states) {
			List<AppState> states = new ArrayList<>();

			AppState[] raw = getStates();
			for (AppState state : raw) {
				if (stateClass.isAssignableFrom(state.getClass()))
					states.add(state);
			}

			if (!states.isEmpty())
				return (T[]) states.toArray();
		}
		return null;
	}

	protected void initializePending() {
		AppState[] raw = getInitializing();
		if (raw.length == 0) {
			return;
		}

		synchronized (states) {
			List<AppState> tmp = Arrays.asList(raw);
			states.addAll(tmp);
			initializing.removeAll(tmp);
		}
		for (AppState state : raw) {
			state.initialize(this, app);
			Gdx.app.debug("AppState", state.getClass().getName() + " is initializing");
		}
	}

	protected void terminatePending() {
		AppState[] raw = getTerminating();
		if (raw.length == 0) {
			return;
		}

		for (AppState state : raw) {
			state.onStateTerminated();
		}

		synchronized (states) {
			List<AppState> bucket = Arrays.asList(raw);
			terminating.removeAll(bucket);
		}
	}

	public void update(float tpf) {
		terminatePending();
		initializePending();

		AppState[] states = getStates();
		for (AppState state : states) {
			if (state.isEnabled()) {
				state.update(tpf);
			}
		}
	}

	public void render() {
		AppState[] states = getStates();
		for (AppState state : states) {
			if (state.isEnabled())
				state.render();
		}
	}

	public void postRender() {
		AppState[] states = getStates();
		for (AppState state : states) {
			if (state.isEnabled())
				state.postRender();
		}
	}
}
