package com.nowabwagel.voxel.world.voxels;

public class VoxelFace {
	public boolean transparent;
	public int type;
	public int side;

	public boolean equals(final VoxelFace face){
		return face.transparent == this.transparent && face.type == this.type;
	}
	
	
}
