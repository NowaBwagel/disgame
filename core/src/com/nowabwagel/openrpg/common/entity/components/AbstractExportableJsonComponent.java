package com.nowabwagel.openrpg.common.entity.components;

import com.badlogic.ashley.core.Component;
import com.github.cliftonlabs.json_simple.JsonObject;

public abstract class AbstractExportableJsonComponent implements Component {

	private String tag;

	public AbstractExportableJsonComponent(String tag) {
		this.tag = tag;
	}

	/**
	 * Should be implement to create json data that can be used in there
	 * componentbuilder.
	 * 
	 * @return
	 */
	public abstract JsonObject getJsonData();

	public JsonObject getJSONObjectTagged() {
		JsonObject obj = getJsonData();
		obj.put("tag", tag);
		return obj;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
