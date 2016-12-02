package com.nowabwagel.voxel.world.voxels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.nowabwagel.voxel.world.World;
import com.nowabwagel.voxel.world.World.Direction;

//FIXME: This class is going to instance a block, so its unique per block
/* FIXME: Make this class go away, blocks should be added to some type of table with just a block type
 * FIXME: Make new class for blocks that need to hold data
 */
public class BlockInstance {

	public static final BlockInstance VOID = new BlockInstance(Block.TEST, new Vector3(0, 0, 0));

	private Block block;
	private Vector3 worldPosition;

	private SquareFaceData[] faces;
	private boolean[] faceExposed;

	public BlockInstance(Block block, Vector3 worldPosition) {
		this.block = block;
		this.worldPosition = worldPosition;
		this.faces = new SquareFaceData[6];
		this.faceExposed = new boolean[6];

		for (int i = 0; i < faceExposed.length; i++)
			faceExposed[i] = false;

		faces[0] = new SquareFaceData(this, World.Direction.NORTH);
		faces[1] = new SquareFaceData(this, World.Direction.EAST);
		faces[2] = new SquareFaceData(this, World.Direction.SOUTH);
		faces[3] = new SquareFaceData(this, World.Direction.WEST);
		faces[4] = new SquareFaceData(this, World.Direction.UP);
		faces[5] = new SquareFaceData(this, World.Direction.DOWN);
	}

	public Block getBlock() {
		return block;
	}

	public Vector3 getWorldPosition() {
		return worldPosition;
	}

	public Vector3 getChunkPosition() {
		float x = worldPosition.x % 16;
		float y = worldPosition.y % 16;
		float z = worldPosition.z % 16;

		return new Vector3(x, y, z);
	}

	public void setFaceExposure(Direction direction, boolean exposure) {
		switch (direction) {
		case DOWN:
			faceExposed[5] = exposure;
			break;
		case EAST:
			faceExposed[1] = exposure;
			break;
		case NORTH:
			faceExposed[0] = exposure;
			break;
		case SOUTH:
			faceExposed[2] = exposure;
			break;
		case UP:
			faceExposed[4] = exposure;
			break;
		case WEST:
			faceExposed[3] = exposure;
			break;
		default:
			break;
		}
	}

	public boolean getFaceExposure(Direction direction) {
		switch (direction) {
		case DOWN:
			return faceExposed[5];
		case EAST:
			return faceExposed[1];
		case NORTH:
			return faceExposed[0];
		case SOUTH:
			return faceExposed[2];
		case UP:
			return faceExposed[4];
		case WEST:
			return faceExposed[3];
		default:
			return false;
		}
	}

	public SquareFaceData[] getFaces() {
		return faces;
	}

	public List<SquareFaceData> getExposedFaces() {
		List<SquareFaceData> list = new ArrayList<SquareFaceData>();

		for (int i = 0; i < faces.length; i++)
			if (faceExposed[i])
				list.add(faces[i]);

		return list;
	}
}
