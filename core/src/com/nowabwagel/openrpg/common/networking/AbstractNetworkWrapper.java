package com.nowabwagel.openrpg.common.networking;

import com.nowabwagel.openrpg.common.networking.packets.EntitySnapshotPacket;

/**
 * AbstractNetworkWrapper should be implemented for both client and server.
 * 
 * @author Noah Bergl
 *
 */
public abstract class AbstractNetworkWrapper {

	private String host;
	private int tcp;
	private int udp;

	private PacketManager packetManager;
	private NetworkSocketListener networkSocketListener;

	/**
	 * 
	 * When implementing A NetworkWrapper, make sure to use the
	 * networkSocketListener included in AbstractNetworkWrapper.
	 * 
	 * @param host
	 *            host name
	 * @param tcp
	 *            tcp port
	 * @param udp
	 *            udp port
	 */
	public AbstractNetworkWrapper() {
		packetManager = new PacketManager();
		networkSocketListener = new NetworkSocketListener(packetManager);
	}

	/**
	 * Attach a IPacketExecutor (which does not have to be unique) to the
	 * PacketManager included.
	 * 
	 * @param class1
	 *            must be unique, if not then Exception will be returned.
	 * @param executor
	 *            executor for class, does not have to be unique
	 * @throws IllegalArgumentException
	 *             When pClass given is not unique
	 */
	public void attachPacketExecutor(Class<? extends Packet> class1, IPacketExecutor executor) throws IllegalArgumentException {
		packetManager.attachPacketExecutor(class1, executor);
	}

	/**
	 * Implementation should handle how Client/Server should handle changing of host
	 * or ports/
	 * 
	 * @param host
	 *            Host name
	 * @param tcp
	 *            tcp Port
	 * @param udp
	 *            udp Port
	 */
	public void connect(String host, int tcp, int udp) {
		this.host = host;
		this.tcp = tcp;
		this.udp = udp;
	}

	/**
	 * Implementation should update the packetManager how Client and Server need.
	 */
	public abstract void update();

	public String getHost() {
		return host;
	}

	public int getTcp() {
		return tcp;
	}

	public int getUdp() {
		return udp;
	}

	public PacketManager getPacketManager() {
		return packetManager;
	}

	public NetworkSocketListener getNetworkSocketListener() {
		return networkSocketListener;
	}
}
