package com.nowabwagel.openrpg.world.chunks.internal;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.nowabwagel.openrpg.world.block.Block;
import com.nowabwagel.openrpg.world.block.BlockManager;
import com.nowabwagel.openrpg.world.chunks.Chunk;
import com.nowabwagel.openrpg.world.chunks.ChunkConstants;
import com.nowabwagel.openrpg.world.chunks.RenderableChunk;

import old.common.world.VoxelFace;

public class ChunkImpl implements Chunk, RenderableChunk {

	private static final int SOUTH = 0;
	private static final Vector3 SOUTH_NORMAL = new Vector3(0, 0, 1);
	private static final int NORTH = 1;
	private static final Vector3 NORTH_NORMAL = new Vector3(0, 0, -1);
	private static final int EAST = 2;
	private static final Vector3 EAST_NORMAL = new Vector3(1, 0, 0);
	private static final int WEST = 3;
	private static final Vector3 WEST_NORMAL = new Vector3(-1, 0, 0);
	private static final int TOP = 4;
	private static final Vector3 TOP_NORMAL = new Vector3(0, 1, 0);
	private static final int BOTTOM = 5;
	private static final Vector3 BOTTOM_NORMAL = new Vector3(0, -1, 0);

	private final GridPoint3 chunkPos = new GridPoint3();

	BlockManager blockManager;

	private final byte[] voxels = new byte[getChunkSizeX() * getChunkSizeY() * getChunkSizeZ()];

	private boolean disposed;
	private boolean ready;
	private volatile boolean dirty;

	private Mesh mesh;

	private MeshBuilder meshBuilder;

	public ChunkImpl(GridPoint3 chunkPos) {
		this.chunkPos.set(chunkPos);
		dirty = true;
		meshBuilder = new MeshBuilder();
	}

	private final Pool<VoxelFace> facePool = new Pool<VoxelFace>() {

		@Override
		protected VoxelFace newObject() {
			return new VoxelFace();
		}

	};

	@Override
	public void updateMesh() {
		if (!dirty)
			return;

		meshBuilder.begin(Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		greedy();
		setMesh(meshBuilder.end());
		dirty = false;
	}

	private void greedy() {
		int partID = 0;

		/*
		 * These are just working variables for the algorithm - almost all taken
		 * directly from Mikola Lysenko's javascript implementation.
		 */
		int i, j, k, l, w, h, u, v, n, side = 0;

		final int[] x = new int[] { 0, 0, 0 };
		final int[] q = new int[] { 0, 0, 0 };
		final int[] du = new int[] { 0, 0, 0 };
		final int[] dv = new int[] { 0, 0, 0 };

		/*
		 * We create a mask - this will contain the groups of matching voxel faces as we
		 * proceed through the chunk in 6 directions - once for each face.
		 */
		final VoxelFace[] mask = new VoxelFace[getChunkSizeX() * getChunkSizeY()];

		/*
		 * These are just working variables to hold two faces during comparison.
		 */
		VoxelFace voxelFace, voxelFace1;

		/**
		 * We start with the lesser-spotted boolean for-loop (also known as the old
		 * flippy floppy).
		 * 
		 * The variable backFace will be TRUE on the first iteration and FALSE on the
		 * second - this allows us to track which direction the indices should run
		 * during creation of the quad.
		 * 
		 * This loop runs twice, and the inner loop 3 times - totally 6 iterations - one
		 * for each voxel face.
		 */
		for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) {

			/*
			 * We sweep over the 3 dimensions - most of what follows is well described by
			 * Mikola Lysenko in his post - and is ported from his Javascript
			 * implementation. Where this implementation diverges, I've added commentary.
			 */
			for (int d = 0; d < 3; d++) {

				u = (d + 1) % 3;
				v = (d + 2) % 3;

				x[0] = 0;
				x[1] = 0;
				x[2] = 0;

				q[0] = 0;
				q[1] = 0;
				q[2] = 0;
				q[d] = 1;

				/*
				 * Here we're keeping track of the side that we're meshing.
				 */
				if (d == 0) {
					side = backFace ? WEST : EAST;
				} else if (d == 1) {
					side = backFace ? BOTTOM : TOP;
				} else if (d == 2) {
					side = backFace ? SOUTH : NORTH;
				}

				/*
				 * We move through the dimension from front to back
				 */
				for (x[d] = -1; x[d] < getChunkSizeX();) {

					/*
					 * ------------------------------------------------------------------- We
					 * compute the mask
					 * -------------------------------------------------------------------
					 */
					n = 0;

					for (x[v] = 0; x[v] < getChunkSizeY(); x[v]++) {

						for (x[u] = 0; x[u] < getChunkSizeX(); x[u]++) {

							/*
							 * Here we retrieve two voxel faces for comparison.
							 */
							voxelFace = (x[d] >= 0) ? getVoxelFace(x[0], x[1], x[2], side) : null;
							voxelFace1 = (x[d] < getChunkSizeX() - 1)
									? getVoxelFace(x[0] + q[0], x[1] + q[1], x[2] + q[2], side)
									: null;

							/*
							 * Note that we're using the equals function in the voxel face class here, which
							 * lets the faces be compared based on any number of attributes.
							 * 
							 * Also, we choose the face to add to the mask depending on whether we're moving
							 * through on a backface or not.
							 */
							mask[n++] = ((voxelFace != null && voxelFace1 != null && voxelFace.equals(voxelFace1)))
									? null
									: backFace ? voxelFace1 : voxelFace;
						}
					}

					x[d]++;

					/*
					 * Now we generate the mesh for the mask
					 */
					n = 0;

					for (j = 0; j < getChunkSizeY(); j++) {

						for (i = 0; i < getChunkSizeX();) {

							if (mask[n] != null) {

								/*
								 * We compute the getChunkSizeX()
								 */
								for (w = 1; i + w < getChunkSizeX() && mask[n + w] != null
										&& mask[n + w].equals(mask[n]); w++) {
								}

								/*
								 * Then we compute getChunkSizeY()
								 */
								boolean done = false;

								for (h = 1; j + h < getChunkSizeY(); h++) {

									for (k = 0; k < w; k++) {

										if (mask[n + k + h * getChunkSizeX()] == null
												|| !mask[n + k + h * getChunkSizeX()].equals(mask[n])) {
											done = true;
											break;
										}
									}

									if (done) {
										break;
									}
								}

								/*
								 * Here we check the "transparent" attribute in the VoxelFace class to ensure
								 * that we don't mesh any culled faces.
								 */
								if (!mask[n].transparent) {
									/*
									 * Add quad
									 */
									x[u] = i;
									x[v] = j;

									du[0] = 0;
									du[1] = 0;
									du[2] = 0;
									du[u] = w;

									dv[0] = 0;
									dv[1] = 0;
									dv[2] = 0;
									dv[v] = h;

									/*
									 * And here we call the quad function in order to render a merged quad in the
									 * scene.
									 * 
									 * We pass mask[n] to the function, which is an instance of the VoxelFace class
									 * containing all the attributes of the face - which allows for variables to be
									 * passed to shaders - for example lighting values used to create ambient
									 * occlusion.
									 */
									quad(new Vector3(x[0], x[1], x[2]),
											new Vector3(x[0] + du[0], x[1] + du[1], x[2] + du[2]),
											new Vector3(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1],
													x[2] + du[2] + dv[2]),
											new Vector3(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]), w, h, mask[n],
											backFace, partID++);
								}

								/*
								 * We zero out the mask
								 */
								for (l = 0; l < h; ++l) {

									for (k = 0; k < w; ++k) {
										mask[n + k + l * getChunkSizeX()] = null;
									}
								}

								/*
								 * And then finally increment the counters and continue
								 */
								i += w;
								n += w;

							} else {

								i++;
								n++;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This function renders a single quad in the scene. This quad may represent
	 * many adjacent voxel faces - so in order to create the illusion of many faces,
	 * you might consider using a tiling function in your voxel shader. For this
	 * reason I've included the quad width and height as parameters.
	 * 
	 * For example, if your texture coordinates for a single voxel face were 0 - 1
	 * on a given axis, they should now be 0 - width or 0 - height. Then you can
	 * calculate the correct texture coordinate in your fragement shader using
	 * coord.xy = fract(coord.xy).
	 * 
	 * 
	 * @param bottomLeft
	 * @param topLeft
	 * @param topRight
	 * @param bottomRight
	 * @param width
	 * @param height
	 * @param voxel
	 * @param backFace
	 */
	void quad(final Vector3 bottomLeft, final Vector3 topLeft, final Vector3 topRight, final Vector3 bottomRight,
			final int width, final int height, final VoxelFace voxel, final boolean backFace, final int partID) {
		VertexInfo v1 = new VertexInfo().setPos(bottomLeft).setNor(getNormal(voxel.side)).setUV(0, 0);
		VertexInfo v2 = new VertexInfo().setPos(bottomRight).setNor(getNormal(voxel.side)).setUV(width, 0);
		VertexInfo v3 = new VertexInfo().setPos(topRight).setNor(getNormal(voxel.side)).setUV(width, height);
		VertexInfo v4 = new VertexInfo().setPos(topLeft).setNor(getNormal(voxel.side)).setUV(0, height);

		MeshPart part = meshBuilder.part("Voxel_MeshPart_" + partID, GL20.GL_TRIANGLES);
		meshBuilder.rect(v1, v2, v3, v4);
		meshBuilder.setUVRange(BlockManager.instance.getRegion(voxel.type));
	}

	Vector3 getNormal(int direction) {
		switch (direction) {
		case SOUTH:
			return SOUTH_NORMAL;
		case NORTH:
			return NORTH_NORMAL;
		case EAST:
			return EAST_NORMAL;
		case WEST:
			return WEST_NORMAL;
		case TOP:
			return TOP_NORMAL;
		case BOTTOM:
			return BOTTOM_NORMAL;
		default:
			return TOP_NORMAL;
		}
	}

	VoxelFace getVoxelFace(final int x, final int y, final int z, final int side) {

		VoxelFace voxelFace = facePool.obtain();

		voxelFace.type = getType(x, y, z);
		voxelFace.side = side;

		return voxelFace;
	}

	private byte getType(int x, int y, int z) {
		return voxels[x + z * getChunkSizeZ() + y * getChunkSizeX() * getChunkSizeY()];
	}

	@Override
	public GridPoint3 getPosition() {
		return chunkPos.cpy();
	}

	@Override
	public Block getBlock(GridPoint3 pos) {
		return null;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return null;
	}

	@Override
	public Block setBlock(GridPoint3 pos, Block type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block setBlock(int x, int y, int z, Block type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3 getChunkOffset() {
		return new Vector3(getChunkOffsetX(), getChunkOffsetY(), getChunkOffsetZ());
	}

	@Override
	public int getChunkOffsetX() {
		return chunkPos.x * getChunkSizeX();
	}

	@Override
	public int getChunkOffsetY() {
		return chunkPos.y * getChunkSizeY();
	}

	@Override
	public int getChunkOffsetZ() {
		return chunkPos.z * getChunkSizeZ();
	}

	@Override
	public GridPoint3 chunkPosToWorldPos(GridPoint3 blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridPoint3 chunkPosToWorldPos(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChunkSizeX() {
		return ChunkConstants.SIZE_X;
	}

	@Override
	public int getChunkSizeY() {
		return ChunkConstants.SIZE_Y;
	}

	@Override
	public int getChunkSizeZ() {
		return ChunkConstants.SIZE_Z;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	@Override
	public void setMesh(Mesh mesh) {
		disposeMesh();
		this.mesh = mesh;
	}

	@Override
	public boolean hasMesh() {
		return this.mesh != null;
	}

	@Override
	public Mesh getMesh() {
		return mesh;
	}

	@Override
	public void disposeMesh() {
		mesh.dispose();

	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public void markReady() {
		this.ready = true;
	}

}
