package old.nowabwagel.openrpg.networking;

import java.io.Serializable;

public abstract class Packet implements Serializable {
	// TODO: Add packet lifespan so packets can expire.
	public PacketType packetType;

	public Packet(PacketType type) {
		this.packetType = type;
	}
}
