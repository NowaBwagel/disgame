package old.nowabwagel.openrpg.networking;

import java.io.Serializable;

public class PacketType implements Serializable{

	public int packetID;

	public PacketType(int ID) {
		this.packetID = ID;
	}

	public static final PacketType RAW_MEESSAGE = new PacketType(0);
	public static final PacketType LOGIN = new PacketType(1);
	public static final PacketType LOGOUT = new PacketType(2);
	// ID: 3-10 saved for other server commands.
	// TODO: Implement Requesting subscription to entity chunks. ie. client joins
	// game, then requests player info. Gets location and sends to server to request
	// for chunk data and to subscribe to get updates for entities in a chunk.
	// player then can unsubscribe when they move out of display load range.
	public static final PacketType WORLD_SNAPSHOT = new PacketType(11);
	// TODO: Drop usable of ENITTY_ADD, and shift to only entity_update, because if
	// an entity is not found during update then it can be added and existing can be
	// updated.
	public static final PacketType ENTITY_ADD = new PacketType(12);
	public static final PacketType ENTITY_REMOVE = new PacketType(13);
	public static final PacketType ENTITY_UPDATE = new PacketType(14);

}
