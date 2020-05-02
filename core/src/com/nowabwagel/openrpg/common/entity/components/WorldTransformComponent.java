package com.nowabwagel.openrpg.common.entity.components;

import com.badlogic.gdx.math.Matrix4;
import com.github.cliftonlabs.json_simple.JsonObject;

public class WorldTransformComponent extends AbstractNetworkReplicatibleComponent {
	private Matrix4 transform;

	public WorldTransformComponent(Matrix4 transform) {
		super("WorldTransformComponent");
		this.transform = transform;
	}

	public Matrix4 getTransform() {
		return transform;
	}

	public void setTransform(Matrix4 transform) {
		this.transform = transform;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonObject getJsonData() {
		JsonObject obj = new JsonObject();
		obj.put("matrix-values", transform);
		return obj;
	}
}
