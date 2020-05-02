package com.nowabwagel.openrpg.common.networking;

import java.io.Serializable;

public abstract class Packet implements Serializable {
	private static final long serialVersionUID = 7268303901444062165L;

	private long time;

	public Packet() {
		time = System.currentTimeMillis();
	}

	public long getTime() {
		return time;
	}
}
