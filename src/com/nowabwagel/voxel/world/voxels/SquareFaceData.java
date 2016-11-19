package com.nowabwagel.voxel.world.voxels;

import com.badlogic.gdx.math.Vector3;
import com.nowabwagel.voxel.world.World.Direction;

public class SquareFaceData {

	private static final Vector3 NORTH_P1 = new Vector3(0.5f, -0.5f, -0.5f);
	private static final Vector3 NORTH_P2 = new Vector3(0.5f, -0.5f, 0.5f);
	private static final Vector3 NORTH_P3 = new Vector3(0.5f, 0.5f, 0.5f);
	private static final Vector3 NORTH_P4 = new Vector3(0.5f, 0.5f, -0.5f);
	private static final Vector3 NORTH_NORMAL = new Vector3(-1, 0, 0);

	private static final Vector3 EAST_P1 = new Vector3(-0.5f, -0.5f, -0.5f);
	private static final Vector3 EAST_P2 = new Vector3(0.5f, -0.5f, -0.5f);
	private static final Vector3 EAST_P3 = new Vector3(0.5f, 0.5f, -0.5f);
	private static final Vector3 EAST_P4 = new Vector3(-0.5f, 0.5f, -0.5f);
	private static final Vector3 EAST_NORMAL = new Vector3(0, 0, -1);

	private static final Vector3 SOUTH_P1 = new Vector3(-0.5f, -0.5f, 0.5f);
	private static final Vector3 SOUTH_P2 = new Vector3(-0.5f, -0.5f, -0.5f);
	private static final Vector3 SOUTH_P3 = new Vector3(-0.5f, 0.5f, -0.5f);
	private static final Vector3 SOUTH_P4 = new Vector3(-0.5f, 0.5f, 0.5f);
	private static final Vector3 SOUTH_NORMAL = new Vector3(1, 0, 0);

	private static final Vector3 WEST_P1 = new Vector3(0.5f, -0.5f, 0.5f);
	private static final Vector3 WEST_P2 = new Vector3(-0.5f, -0.5f, 0.5f);
	private static final Vector3 WEST_P3 = new Vector3(-0.5f, 0.5f, 0.5f);
	private static final Vector3 WEST_P4 = new Vector3(0.5f, 0.5f, 0.5f);
	private static final Vector3 WEST_NORMAL = new Vector3(0, 0, 1);

	private static final Vector3 UP_P1 = new Vector3(-0.5f, 0.5f, -0.5f);
	private static final Vector3 UP_P2 = new Vector3(0.5f, 0.5f, -0.5f);
	private static final Vector3 UP_P3 = new Vector3(0.5f, 0.5f, 0.5f);
	private static final Vector3 UP_P4 = new Vector3(-0.5f, 0.5f, 0.5f);
	private static final Vector3 UP_NORMAL = new Vector3(0, 1, 0);

	private static final Vector3 DOWN_P1 = new Vector3(-0.5f, -0.5f, 0.5f);
	private static final Vector3 DOWN_P2 = new Vector3(0.5f, -0.5f, 0.5f);
	private static final Vector3 DOWN_P3 = new Vector3(0.5f, -0.5f, -0.5f);
	private static final Vector3 DOWN_P4 = new Vector3(-0.5f, -0.5f, -0.5f);
	private static final Vector3 DOWN_NORMAL = new Vector3(0, 1, 0);

	private Direction side;
	private BlockInstance parent;

	private Vector3 P1;
	private Vector3 P2;
	private Vector3 P3;
	private Vector3 P4;
	private Vector3 NORMAL;

	// FIXME: this class is just eating ram, calculations can be done on demand.
	// So remove class and just use static variables
	public SquareFaceData(BlockInstance parent, Direction side) {
		this.parent = parent;
		this.side = side;

		switch (side) {
		case DOWN:
			P1 = DOWN_P1.cpy().add(getOffset());
			P2 = DOWN_P2.cpy().add(getOffset());
			P3 = DOWN_P3.cpy().add(getOffset());
			P4 = DOWN_P4.cpy().add(getOffset());
			NORMAL = DOWN_NORMAL;
			break;
		case EAST:
			P1 = EAST_P1.cpy().add(getOffset());
			P2 = EAST_P2.cpy().add(getOffset());
			P3 = EAST_P3.cpy().add(getOffset());
			P4 = EAST_P4.cpy().add(getOffset());
			NORMAL = EAST_NORMAL;
			break;
		case NORTH:
			P1 = NORTH_P1.cpy().add(getOffset());
			P2 = NORTH_P2.cpy().add(getOffset());
			P3 = NORTH_P3.cpy().add(getOffset());
			P4 = NORTH_P4.cpy().add(getOffset());
			NORMAL = NORTH_NORMAL;
			break;
		case SOUTH:
			P1 = SOUTH_P1.cpy().add(getOffset());
			P2 = SOUTH_P2.cpy().add(getOffset());
			P3 = SOUTH_P3.cpy().add(getOffset());
			P4 = SOUTH_P4.cpy().add(getOffset());
			NORMAL = SOUTH_NORMAL;
			break;
		case UP:
			P1 = UP_P1.cpy().add(getOffset());
			P2 = UP_P2.cpy().add(getOffset());
			P3 = UP_P3.cpy().add(getOffset());
			P4 = UP_P4.cpy().add(getOffset());
			NORMAL = UP_NORMAL;
			break;
		case WEST:
			P1 = WEST_P1.cpy().add(getOffset());
			P2 = WEST_P2.cpy().add(getOffset());
			P3 = WEST_P3.cpy().add(getOffset());
			P4 = WEST_P4.cpy().add(getOffset());
			NORMAL = WEST_NORMAL;
			break;
		default:
			break;

		}
	}

	public Direction getDirection() {
		return side;
	}

	public Vector3 getOffset() {
		return parent.getChunkPosition();
	}

	public BlockInstance getParent() {
		return parent;
	}

	public Vector3 getP1() {
		return P1;
	}

	public Vector3 getP2() {
		return P2;
	}

	public Vector3 getP3() {
		return P3;
	}

	public Vector3 getP4() {
		return P4;
	}

	public Vector3 getNORMAL() {
		return NORMAL;
	}
}
