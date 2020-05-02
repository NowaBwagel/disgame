package old.nowabwagel.openrpg.networking;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.jmr.wrapper.common.Connection;

public class PacketManager {
	private static final int PACKET_LIFESPAN = 5000; // 5000ms

	private Map<Integer, IPacketExecutor> executors;
	private Queue<PacketWrapper> packetQueue;

	// TODO: When more stress is created test different data types for performance.
	public PacketManager() {
		this.executors = new LinkedHashMap<>();
		this.packetQueue = new LinkedList<>();
	}

	public boolean attachPacketExecutor(PacketType type, IPacketExecutor executor) {
		if (executors.containsKey(type)) {
			System.out.println("I already contain that key");
			return false;
		}

		executors.put(type.packetID, executor);
		return true;
	}

	protected boolean addPacket(Connection sender, Packet packet) {
		return addPacket(new PacketWrapper(sender, packet));
	}

	protected boolean addPacket(PacketWrapper packetWrapper) {
		if (!executors.containsKey(packetWrapper.packet.packetType.packetID)) {
			System.out.println("I Don't know how to handle this packet" + packetWrapper.packet);
			return false;
		}

		packetQueue.add(packetWrapper);
		return true;
	}

	public void executePackets() {
		// long startTime = System.currentTimeMillis();
		PacketWrapper packetWrapper;
		while ((packetWrapper = packetQueue.poll()) != null) {
			if ((System.currentTimeMillis() - packetWrapper.time) > PACKET_LIFESPAN)
				continue;

			IPacketExecutor executor = executors.get(packetWrapper.packet.packetType.packetID);
			executor.execute(packetWrapper.sender, packetWrapper.packet);
		}
	}

	public void executeSinglePacket() throws InterruptedException {
		PacketWrapper packetWrapper = packetQueue.poll();
		if (packetWrapper != null) {
			System.out.println("trying to execute packet");
			IPacketExecutor executor = executors.get(packetWrapper.packet.packetType.packetID);
			executor.execute(packetWrapper.sender, packetWrapper.packet);
		} else
			Thread.sleep(10);
	}
}
