package old.nowabwagel.openrpg.networking.packets;

import old.nowabwagel.openrpg.entity.components.NetworkComponent;
import old.nowabwagel.openrpg.entity.components.TransformComponent;
import old.nowabwagel.openrpg.networking.Packet;
import old.nowabwagel.openrpg.networking.PacketType;

public class EntityUpdatePacket extends Packet {
	
	public NetworkComponent netCom;
	public TransformComponent tranCom;

	public EntityUpdatePacket() {
		super(PacketType.ENTITY_UPDATE);
	}

}
