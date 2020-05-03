package com.nowabwagel.openrpg.server.networking;

import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.server.Server;

import old.common.networking.AbstractNetworkWrapper;

public class ServerNetworkWrapper extends AbstractNetworkWrapper {

	private Server server;


	@Override
	public void update() {
		// Server could be overloaded with inbounds packets and not update entity
		// updating and server replications.
		// TODO: Check if this update method for the server should just be on separate
		// thread and handle all packets per call.
		this.getPacketManager().update(10);
	}

	@Override
	public void connect(String host, int tcp, int udp) {
		super.connect(host, tcp, udp);
		if (server != null && server.isConnected()) {
			server.close();
		}

		try {
			server = new Server(tcp, udp);
			server.setListener(this.getNetworkSocketListener());
			if (server.isConnected()) {
				System.out.println("Server connected TCP: " + tcp + ", UDP: " + udp + ".");
			}
		} catch (NNCantStartServer e) {
			e.printStackTrace();
		}
	}

}
