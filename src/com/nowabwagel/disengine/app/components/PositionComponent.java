package com.nowabwagel.disengine.app.components;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.nowabwagel.disengine.app.entitysystem.DataComponent;

public class PositionComponent extends DataComponent {

	private Vector3 position;
	private Quaternion rotation;

	public PositionComponent(Vector3 position, Quaternion rotation) {
		this.position = position.cpy();
		this.rotation = rotation.cpy();
	}

	public Vector3 getPosition() {
		return position;
	}

	public Quaternion getRotation() {
		return rotation;
	}
}
