package com.nowabwagel.openrpg.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntitySnapshotBundle implements Serializable {

	private static final long serialVersionUID = -577118591297156872L;

	private List<EntitySnapshot> snapshots;

	public EntitySnapshotBundle() {
		snapshots = new ArrayList<>();
	}

	public void addSnapshot(EntitySnapshot snapshot) {
		snapshots.add(snapshot);
	}

	public List<EntitySnapshot> getSnapshots() {
		return snapshots;
	}
}
