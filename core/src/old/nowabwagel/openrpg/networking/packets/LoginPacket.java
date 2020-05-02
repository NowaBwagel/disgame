package old.nowabwagel.openrpg.networking.packets;

import old.nowabwagel.openrpg.networking.Packet;
import old.nowabwagel.openrpg.networking.PacketType;

public class LoginPacket extends Packet{

	public LoginPacket() {
		super(PacketType.LOGIN);
	}

}
