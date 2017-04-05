package com.nowabwagel.disengine.app.components;

import com.nowabwagel.disengine.app.entitysystem.DataComponent;

public class InSceneComponent extends DataComponent {
	private boolean value;

	public InSceneComponent(boolean inScene) {
		value = inScene;
	}

	public boolean getValue() {
		return value;
	}
}
