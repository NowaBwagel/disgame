package com.nowabwagel.disgame.components;

import com.nowabwagel.disgame.entities.Entity;

public abstract class EntityComponent {
	private Entity parent;

	public void input(float delta) {

	}

	public void update(float delta) {

	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
}
