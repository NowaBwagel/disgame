package old.common.entity;

import java.io.Serializable;

import com.github.cliftonlabs.json_simple.JsonObject;

public class EntitySnapshot implements Serializable {
	private static final long serialVersionUID = -1069392301133075653L;

	public enum Type {
		Full, Update,
	}

	/**
	 * What kind of smapshot is this(Full is entire entity) (Update is just
	 * NetworkID, Type, and Changed components)
	 */
	public Type snapshotType;

	/**
	 * Entity NetworkID
	 */
	public int entityID;
	/**
	 * Entity Factory Type
	 */
	public String entityType;
	/**
	 * Entity Components
	 */
	public JsonObject components;

}
