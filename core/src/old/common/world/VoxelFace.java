package old.common.world;

import com.badlogic.gdx.utils.Pool;

public class VoxelFace implements Pool.Poolable {
	public boolean transparent;
	public int type;
	public int side;
	
	public VoxelFace() {
		reset();
	}

	public boolean equals(final VoxelFace face) {
		return face.transparent == this.transparent && face.type == this.type;
	}

	@Override
	public void reset() {
		this.transparent = false;
		this.type = 0;
		this.side = 0;

	}
}
