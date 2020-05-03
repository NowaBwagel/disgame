package old.common.networking.packets;

import old.common.entity.EntitySnapshotBundle;
import old.common.networking.Packet;

public class EntitySnapshotPacket extends Packet {

	private static final long serialVersionUID = -3825385283797047559L;

	public EntitySnapshotBundle bundle;

	public EntitySnapshotPacket(EntitySnapshotBundle bundle) {
		this.bundle = bundle;
	}
}
