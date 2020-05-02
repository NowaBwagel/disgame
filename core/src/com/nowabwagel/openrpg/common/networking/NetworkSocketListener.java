package com.nowabwagel.openrpg.common.networking;

import com.badlogic.gdx.Gdx;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

public class NetworkSocketListener implements SocketListener {

	private PacketManager packetManager;

	public NetworkSocketListener(PacketManager packetManager) {
		this.packetManager = packetManager;
	}

	@Override
	public void received(Connection con, Object object) {
		if (object instanceof Packet) {
			Packet packet = (Packet) object;
			//Gdx.app.debug("NetworkSocketListener", "Received Packet: " + object);
			this.packetManager.addPacket(new PacketWrapper(con, packet));
			// TODO: Disable TestAlivePing if it is not needed
		} else if (object.equals("TestAlivePing")) {
			Gdx.app.debug("NetworkSocketListener", "Received AlivePing");
		} else {
			Gdx.app.error("NetworkSocketListener", "Received an unknown object " + object);
		}
	}

	@Override
	public void connected(Connection con) {

	}

	@Override
	public void disconnected(Connection con) {

	}

}
