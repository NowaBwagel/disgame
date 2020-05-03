package com.nowabwagel.openrpg.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Matrix4;
import com.github.cliftonlabs.json_simple.JsonObject;

import old.common.entity.components.AbstractNetworkReplicatibleComponent;

public class WorldTransformComponent implements Component {
	private Matrix4 transform;

	public WorldTransformComponent(Matrix4 transform) {
		this.transform = transform;
	}

	public Matrix4 getTransform() {
		return transform;
	}

	public void setTransform(Matrix4 transform) {
		this.transform = transform;
	}
}
