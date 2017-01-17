package com.nowabwagel.disgame.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Matrix4;
import com.nowabwagel.disgame.components.EntityComponent;

public class Entity {

	private ArrayList<Entity> children;
	private ArrayList<EntityComponent> components;
	private Matrix4 transform;

	private RenderableProvider renderable;

	public RenderableProvider getRenderable() {
		return renderable;
	}

}
