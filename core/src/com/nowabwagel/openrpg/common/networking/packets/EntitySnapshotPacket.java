package com.nowabwagel.openrpg.common.networking.packets;

import com.nowabwagel.openrpg.common.entity.EntitySnapshotBundle;
import com.nowabwagel.openrpg.common.networking.Packet;

public class EntitySnapshotPacket extends Packet {

	private static final long serialVersionUID = -3825385283797047559L;

	public EntitySnapshotBundle bundle;

	public EntitySnapshotPacket(EntitySnapshotBundle bundle) {
		this.bundle = bundle;
	}
}
