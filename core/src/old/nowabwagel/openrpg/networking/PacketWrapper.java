package old.nowabwagel.openrpg.networking;

import com.jmr.wrapper.common.Connection;

public class PacketWrapper {

	public Connection sender;
	public Packet packet;
	public long time;

	public PacketWrapper(Connection sender, Packet packet) {
		this.sender = sender;
		this.packet = packet;
		this.time = System.currentTimeMillis();
	}
}
