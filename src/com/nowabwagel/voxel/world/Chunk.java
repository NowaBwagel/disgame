package com.nowabwagel.voxel.world;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.nowabwagel.voxel.world.World.Direction;
import com.nowabwagel.voxel.world.voxels.Block;
import com.nowabwagel.voxel.world.voxels.Block.BlockOpacity;
import com.nowabwagel.voxel.world.voxels.BlockInstance;
import com.nowabwagel.voxel.world.voxels.SquareFaceData;

public class Chunk implements WorldRenderable {

	public final BlockInstance[] blocks;
	// X
	public final int width;
	// Y
	public final int height;
	// Z
	public final int depth;

	public final Vector3 offset;
	private boolean notCurrent;
	private ModelInstance chunkModel;

	public Chunk(int width, int height, int depth, Vector3 offset) {
		this.blocks = new BlockInstance[width * height * depth];
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.offset = offset;
		this.notCurrent = true;
	}

	public void setFast(int x, int y, int z, Block type) {
		int index = x + z * width + y * width * height;
		BlockInstance block = new BlockInstance(type, offset.add(x, y, z));
		blocks[index] = block;

		updateFace(x, y, z);
	}

	public BlockInstance get(int x, int y, int z) {
		if (x < 0 || x >= width)
			return BlockInstance.VOID;
		if (y < 0 || y >= height)
			return BlockInstance.VOID;
		if (z < 0 || z >= depth)
			return BlockInstance.VOID;
		return getFast(x, y, z);
	}

	public BlockInstance getFast(int x, int y, int z) {
		int index = x + z * width + y * width * height;
		return blocks[index];
	}

	// TODO: Idea, Maybe hide internal? like if transparent next to opaque hide
	// the transparent sides that are not touching null
	private void updateFace(int x, int y, int z) {
		BlockInstance me = blocks[x + z * width + y * width * height];

		con_checkBlock(me, (get(x + 1, y, z)), Direction.NORTH, Direction.SOUTH);
		con_checkBlock(me, (get(x, y, z - 1)), Direction.EAST, Direction.WEST);
		con_checkBlock(me, (get(x - 1, y, z)), Direction.SOUTH, Direction.NORTH);
		con_checkBlock(me, (get(x, y, z + 1)), Direction.WEST, Direction.EAST);
		con_checkBlock(me, (get(x, y + 1, z)), Direction.UP, Direction.DOWN);
		con_checkBlock(me, (get(x, y - 1, z)), Direction.DOWN, Direction.UP);
		notCurrent = true;
	}

	/**
	 * 
	 * @param me
	 *            Block that is being updated
	 * @param nei
	 *            Neiboro block being checked on
	 * @param dir
	 *            direction from me to nie
	 * @param opsiDir
	 *            Direction back to me
	 */
	private void con_checkBlock(BlockInstance me, BlockInstance nei, Direction dir, Direction opsiDir) {
		if (nei != null && nei != BlockInstance.VOID) {
			if (me.getBlock().getOpacity() == BlockOpacity.Opaque)
				nei.setFaceExposure(opsiDir, false);
			else
				nei.setFaceExposure(opsiDir, true);
			if (nei.getBlock().getOpacity() == BlockOpacity.Opaque)
				me.setFaceExposure(dir, false);
			else
				nei.setFaceExposure(dir, true);
		} else {
			me.setFaceExposure(dir, true);
		}

		// if (nei != null && nei != BlockInstance.VOID) {
		// if (nei.getBlock().getOpacity() == BlockOpacity.Opaque) {
		// System.out.println("HIDE");
		// me.setFaceExposure(dir, false);
		// } else {
		// me.setFaceExposure(dir, true);
		// System.out.println("Show 1");
		// }
		//
		// // Disables internal exposure (i.e. seeing only air/void touching
		// // sides of a transparent block)
		// nei.setFaceExposure(opsiDir, false);
		// } else {
		// me.setFaceExposure(dir, true);
		// System.out.println("Show 2");
		// }
	}

	@Override
	public ModelInstance getModelInstance() {
		if (notCurrent) {
			int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
					| VertexAttributes.Usage.TextureCoordinates;
			ModelBuilder modelBuilder = new ModelBuilder();
			modelBuilder.begin();
			BlockInstance instance = null;
			SquareFaceData face = null;

			for (int i = 0; i < blocks.length; i++) {
				if ((instance = blocks[i]) != null) {
					SquareFaceData[] faces = instance.getFaces();
					for (int j = 0; j < faces.length; j++) {
						face = faces[j];
						if (instance.getFaceExposure(face.getDirection()))
							modelBuilder
									.part("face" + i, GL20.GL_TRIANGLES, attr,
											new Material(TextureAttribute.createDiffuse(
													face.getParent().getBlock().getFaceTexture(face.getDirection()))))
									.rect(face.getP1(), face.getP2(), face.getP3(), face.getP4(), face.getNORMAL());
					}
				}
			}
			chunkModel = new ModelInstance(modelBuilder.end());
		}
		return chunkModel;
	}

}
