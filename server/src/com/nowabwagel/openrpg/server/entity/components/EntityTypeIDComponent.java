package com.nowabwagel.openrpg.server.entity.components;

import com.badlogic.ashley.core.Component;

public class EntityTypeIDComponent implements Component {

	private String typeID;

	public EntityTypeIDComponent(String typeID) {
		this.typeID = typeID;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
}
