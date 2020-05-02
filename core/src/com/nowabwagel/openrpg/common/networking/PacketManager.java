package com.nowabwagel.openrpg.common.networking;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PacketManager {
	public static final int PACKET_LIFESPAN = 5000; // 5 seconds

	private boolean resetLock;
	private Map<Class<? extends Packet>, IPacketExecutor> executors;
	private Queue<PacketWrapper> packetQueue;

	// TODO: Create a unit test once more packets are made, to test different data
	// storage types for speed.
	public PacketManager() {
		this.resetLock = false;
		this.executors = new LinkedHashMap<>();
		this.packetQueue = new LinkedList<>();
	}

	public void attachPacketExecutor(Class<? extends Packet> class1, IPacketExecutor executor)
			throws IllegalArgumentException {
		if (executors.containsKey(class1))
			throw new IllegalArgumentException("Trying to register executor for " + class1 + " is already registered.");
		else
			executors.put(class1, executor);
	}

	/**
	 * Drop all current packets to be handled, and Drop all executors.
	 */
	public void resetManager() {
		resetLock = true;
		executors.clear();
		packetQueue.clear();
		resetLock = false;
	}

	public void addPacket(PacketWrapper wrapper) {
		packetQueue.add(wrapper);
	}

	public void update(int maxPackets) {
		PacketWrapper wrapper;
		while (!resetLock && (maxPackets-- >= 0 && (wrapper = packetQueue.poll()) != null)) {
			if (System.currentTimeMillis() - wrapper.getPacket().getTime() > PACKET_LIFESPAN)
				continue; // Skip because longer then lifespan.
			else
				executors.get(wrapper.getPacket().getClass()).execute(wrapper.getSender(), wrapper.getPacket());
		}
	}

}
