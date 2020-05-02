package old.nowabwagel.openrpg.networking;

import com.jmr.wrapper.client.Client;

import old.nowabwagel.openrpg.networking.packets.LoginPacket;

public class NetworkClientWrapper {
	public static NetworkClientWrapper inst;

	private final Client client;
	private PacketManager packetManager;
	private NetworkPacketListener networkPacketListener;

	// TODO: Radial Priority for Updates.
	public NetworkClientWrapper(String host, int tcp, int udp) {
		client = new Client(host, tcp, udp);
		packetManager = new PacketManager();
		networkPacketListener = new NetworkPacketListener(packetManager);

		client.setListener(networkPacketListener);

		client.connect();
		if (client.isConnected()) {
			System.out.println("Connected to Server");
			client.getServerConnection().sendTcp(new LoginPacket());
		}

		inst = this;
	}

	public boolean attachPacketExecutor(PacketType type, IPacketExecutor executor) {
		return packetManager.attachPacketExecutor(type, executor);
	}
}
