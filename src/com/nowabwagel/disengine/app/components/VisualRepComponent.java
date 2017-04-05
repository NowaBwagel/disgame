package com.nowabwagel.disengine.app.components;

import com.nowabwagel.disengine.app.entitysystem.DataComponent;

public class VisualRepComponent extends DataComponent {

	private String assetName;

	public VisualRepComponent(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetName() {
		return assetName;
	}
}
