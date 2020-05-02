package old.nowabwagel.openrpg.server;

import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.server.Server;

import old.nowabwagel.openrpg.networking.NetworkPacketListener;
import old.nowabwagel.openrpg.networking.PacketManager;

public class PacketServer implements Runnable {
	private Server server;
	public PacketManager packetManager;
	private NetworkPacketListener networkPacketListener;

	public PacketServer() {
		packetManager = new PacketManager();
		networkPacketListener = new NetworkPacketListener(packetManager);
		try {
			server = new Server(4395, 4395);
			server.setListener(networkPacketListener);
		} catch (NNCantStartServer e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				packetManager.executeSinglePacket();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
