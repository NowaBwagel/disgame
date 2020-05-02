package com.nowabwagel.openrpg.common.networking;

import java.io.Serializable;

import com.jmr.wrapper.common.Connection;

public class PacketWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3178606609334687192L;

	private Connection sender;
	private Packet packet;

	public PacketWrapper(Connection sender, Packet packet) {
		this.sender = sender;
		this.packet = packet;
	}

	public Connection getSender() {
		return sender;
	}

	public Packet getPacket() {
		return packet;
	}
}
