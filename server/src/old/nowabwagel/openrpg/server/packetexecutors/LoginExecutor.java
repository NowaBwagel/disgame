package old.nowabwagel.openrpg.server.packetexecutors;

import com.jmr.wrapper.common.Connection;

import old.nowabwagel.openrpg.networking.IPacketExecutor;
import old.nowabwagel.openrpg.networking.Packet;
import old.nowabwagel.openrpg.server.ClientManagerSystem;

public class LoginExecutor implements IPacketExecutor {

	@Override
	public void execute(Connection sender, Packet packet) {
		System.out.println("Client Loggin in");
		ClientManagerSystem.addClient(sender);
	}

}
