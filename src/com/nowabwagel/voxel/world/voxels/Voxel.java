package com.nowabwagel.voxel.world.voxels;

public class Voxel {
	private boolean transparent;
	private int type;

	public Voxel(boolean transparent, int type) {
		this.transparent = transparent;
		this.type = type;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
