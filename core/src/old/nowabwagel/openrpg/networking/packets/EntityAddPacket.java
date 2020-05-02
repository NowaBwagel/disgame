package old.nowabwagel.openrpg.networking.packets;

import old.nowabwagel.openrpg.entity.EntitySnapshot;
import old.nowabwagel.openrpg.networking.Packet;
import old.nowabwagel.openrpg.networking.PacketType;

public class EntityAddPacket extends Packet{
	
	public EntitySnapshot snapshot;

	public EntityAddPacket() {
		super(PacketType.ENTITY_ADD);
	}

}
