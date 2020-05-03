package old.common.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.cliftonlabs.json_simple.JsonObject;

public class EntityTemplate {
	private String ID;
	private Map<String, JsonObject> templateData;

	public EntityTemplate(String ID) {
		this.ID = ID;
		this.templateData = new HashMap<>();
	}

	public String getID() {
		return ID;
	}

	public void setTemplateData(String tag, JsonObject data) {
		this.templateData.put(tag, data);
	}

	public Set<String> getTemplateTags() {
		return templateData.keySet();
	}

	public JsonObject getTemplateData(String tag) {
		return templateData.get(tag);
	}
}
