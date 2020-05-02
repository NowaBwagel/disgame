package com.nowabwagel.openrpg.common.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ModelInstanceComponent implements Component {
	private ModelInstance modelInstance;

	public ModelInstanceComponent(ModelInstance modelInstance) {
		this.setModelInstance(modelInstance);
	}

	public ModelInstance getModelInstance() {
		return modelInstance;
	}

	public void setModelInstance(ModelInstance modelInstance) {
		this.modelInstance = modelInstance;
	}
}
