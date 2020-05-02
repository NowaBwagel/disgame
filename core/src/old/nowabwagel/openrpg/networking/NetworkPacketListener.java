package old.nowabwagel.openrpg.networking;

import com.badlogic.gdx.Gdx;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

public class NetworkPacketListener implements SocketListener {
	private PacketManager packetManager;

	public NetworkPacketListener(PacketManager packetManager) {
		this.packetManager = packetManager;
	}

	@Override
	public void received(Connection con, Object object) {
		if (object instanceof Packet) {
			Packet packet = (Packet) object;
			Gdx.app.log("DEBUG", "Received Packet: " + object);
			this.packetManager.addPacket(new PacketWrapper(con, packet));
		} else
			Gdx.app.log("ERROR", "Packet Received is not Known: " + object);
	}

	@Override
	public void connected(Connection con) {

	}

	@Override
	public void disconnected(Connection con) {

	}

}
