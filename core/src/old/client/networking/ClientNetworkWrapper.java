package old.client.networking;

import com.jmr.wrapper.client.Client;
import com.jmr.wrapper.common.Connection;

import old.common.networking.AbstractNetworkWrapper;

public class ClientNetworkWrapper extends AbstractNetworkWrapper {

	private Client client;
	private Connection serverConnection;

	@Override
	public void update() {
		this.getPacketManager().update(999); // Client should want to execute all packets per frame.
	}

	@Override
	public void connect(String host, int tcp, int udp) {
		super.connect(host, tcp, udp);
		if (client != null && client.isConnected()) {
			client.close();
		}
		client = new Client(host, tcp, udp);
		client.setListener(this.getNetworkSocketListener());
		client.connect();
		if (client.isConnected()) {
			System.out.println("Connected to Server :D");
			serverConnection = client.getServerConnection();
			// TODO:Send LoginPacket
		}
	}

	public Connection getServerConnection() {
		return serverConnection;
	}

}
