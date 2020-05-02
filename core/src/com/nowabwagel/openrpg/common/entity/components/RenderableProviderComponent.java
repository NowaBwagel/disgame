package com.nowabwagel.openrpg.common.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;

public class RenderableProviderComponent implements Component {

	public RenderableProvider provider;

	public RenderableProviderComponent(RenderableProvider provider) {
		this.provider = provider;
	}
}
