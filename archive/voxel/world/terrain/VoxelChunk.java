package com.nowabwagel.voxel.world.terrain;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.nowabwagel.voxel.world.WorldRenderable;
import com.nowabwagel.voxel.world.voxels.VoxelFace;

public class VoxelChunk implements WorldRenderable {

	private static final int VOXEL_SIZE = 1;

	private static final int CHUNK_WIDTH = 16;
	private static final int CHUNK_HEIGHT = 16;

	private final VoxelFace[][][] voxels = new VoxelFace[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];;

	private static final int SOUTH = 0;
	private static final int NORTH = 1;
	private static final int EAST = 2;
	private static final int WEST = 3;
	private static final int TOP = 4;
	private static final int BOTTOM = 5;

	private ModelInstance modelInstance;

	private int locX, locY, locZ;

	private Texture texture;
	private Texture notexture;

	public VoxelChunk(int locX, int locY, int locZ) {
		texture = new Texture("unpacked/block_stone.png");
		notexture = new Texture("unpacked/notexture.png");
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		notexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;

		VoxelFace face;

		for (int i = 0; i < CHUNK_WIDTH; i++) {

			for (int j = 0; j < CHUNK_HEIGHT; j++) {

				for (int k = 0; k < CHUNK_HEIGHT; k++) {

					if (i > CHUNK_WIDTH / 2 && i < CHUNK_WIDTH * 0.75 && j > CHUNK_HEIGHT / 2 && j < CHUNK_HEIGHT * 0.75
							&& k > CHUNK_HEIGHT / 2 && k < CHUNK_HEIGHT * 0.75) {

						/*
						 * We add a set of voxels of type 1 at the top-right of
						 * the chunk.
						 * 
						 */
						face = new VoxelFace();
						face.type = 1;

						/*
						 * To see an example of face culling being used in
						 * combination with greedy meshing, you could set the
						 * trasparent attribute to true.
						 */
						// face.transparent = true;

					} else if (i == 0) {

						/*
						 * We add a set of voxels of type 2 on the left of the
						 * chunk.
						 */
						face = new VoxelFace();
						face.type = 2;

					} else {

						/*
						 * And the rest are set to type 3.
						 */
						face = new VoxelFace();
						face.type = 3;
					}

					voxels[i][j][k] = face;
				}
			}
		}

		/*
		 * And now that the sample data is prepared, we launch the greedy
		 * meshing.
		 */
		greedy();
	}

	/**
	 * 
	 */
	void greedy() {
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();

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
		 * We create a mask - this will contain the groups of matching voxel
		 * faces as we proceed through the chunk in 6 directions - once for each
		 * face.
		 */
		final VoxelFace[] mask = new VoxelFace[CHUNK_WIDTH * CHUNK_HEIGHT];

		/*
		 * These are just working variables to hold two faces during comparison.
		 */
		VoxelFace voxelFace, voxelFace1;

		/**
		 * We start with the lesser-spotted boolean for-loop (also known as the
		 * old flippy floppy).
		 * 
		 * The variable backFace will be TRUE on the first iteration and FALSE
		 * on the second - this allows us to track which direction the indices
		 * should run during creation of the quad.
		 * 
		 * This loop runs twice, and the inner loop 3 times - totally 6
		 * iterations - one for each voxel face.
		 */
		for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) {

			/*
			 * We sweep over the 3 dimensions - most of what follows is well
			 * described by Mikola Lysenko in his post - and is ported from his
			 * Javascript implementation. Where this implementation diverges,
			 * I've added commentary.
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
				for (x[d] = -1; x[d] < CHUNK_WIDTH;) {

					/*
					 * ---------------------------------------------------------
					 * ---------- We compute the mask
					 * ---------------------------------------------------------
					 * ----------
					 */
					n = 0;

					for (x[v] = 0; x[v] < CHUNK_HEIGHT; x[v]++) {

						for (x[u] = 0; x[u] < CHUNK_WIDTH; x[u]++) {

							/*
							 * Here we retrieve two voxel faces for comparison.
							 */
							voxelFace = (x[d] >= 0) ? getVoxelFace(x[0], x[1], x[2], side) : null;
							voxelFace1 = (x[d] < CHUNK_WIDTH - 1)
									? getVoxelFace(x[0] + q[0], x[1] + q[1], x[2] + q[2], side) : null;

							/*
							 * Note that we're using the equals function in the
							 * voxel face class here, which lets the faces be
							 * compared based on any number of attributes.
							 * 
							 * Also, we choose the face to add to the mask
							 * depending on whether we're moving through on a
							 * backface or not.
							 */
							mask[n++] = ((voxelFace != null && voxelFace1 != null && voxelFace.equals(voxelFace1)))
									? null : backFace ? voxelFace1 : voxelFace;
						}
					}

					x[d]++;

					/*
					 * Now we generate the mesh for the mask
					 */
					n = 0;

					for (j = 0; j < CHUNK_HEIGHT; j++) {

						for (i = 0; i < CHUNK_WIDTH;) {

							if (mask[n] != null) {

								/*
								 * We compute the width
								 */
								for (w = 1; i + w < CHUNK_WIDTH && mask[n + w] != null
										&& mask[n + w].equals(mask[n]); w++) {
								}

								/*
								 * Then we compute height
								 */
								boolean done = false;

								for (h = 1; j + h < CHUNK_HEIGHT; h++) {

									for (k = 0; k < w; k++) {

										if (mask[n + k + h * CHUNK_WIDTH] == null
												|| !mask[n + k + h * CHUNK_WIDTH].equals(mask[n])) {
											done = true;
											break;
										}
									}

									if (done) {
										break;
									}
								}

								/*
								 * Here we check the "transparent" attribute in
								 * the VoxelFace class to ensure that we don't
								 * mesh any culled faces.
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
									 * And here we call the quad function in
									 * order to render a merged quad in the
									 * scene.
									 * 
									 * We pass mask[n] to the function, which is
									 * an instance of the VoxelFace class
									 * containing all the attributes of the face
									 * - which allows for variables to be passed
									 * to shaders - for example lighting values
									 * used to create ambient occlusion.
									 */
									quad(modelBuilder, new Vector3(x[0], x[1], x[2]),
											new Vector3(x[0] + du[0], x[1] + du[1], x[2] + du[2]),
											new Vector3(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1],
													x[2] + du[2] + dv[2]),
											new Vector3(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]), w, h, mask[n],
											backFace);
								}

								/*
								 * We zero out the mask
								 */
								for (l = 0; l < h; ++l) {

									for (k = 0; k < w; ++k) {
										mask[n + k + l * CHUNK_WIDTH] = null;
									}
								}

								/*
								 * And then finally increment the counters and
								 * continue
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
		modelInstance = new ModelInstance(modelBuilder.end(), locX, locY, locZ);
	}

	/**
	 * This function returns an instance of VoxelFace containing the attributes
	 * for one side of a voxel. In this simple demo we just return a value from
	 * the sample data array. However, in an actual voxel engine, this function
	 * would check if the voxel face should be culled, and set per-face and
	 * per-vertex values as well as voxel values in the returned instance.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param face
	 * @return
	 */
	VoxelFace getVoxelFace(final int x, final int y, final int z, final int side) {

		VoxelFace voxelFace = voxels[x][y][z];

		voxelFace.side = side;

		return voxelFace;
	}

	/**
	 * This function renders a single quad in the scene. This quad may represent
	 * many adjacent voxel faces - so in order to create the illusion of many
	 * faces, you might consider using a tiling function in your voxel shader.
	 * For this reason I've included the quad width and height as parameters.
	 * 
	 * For example, if your texture coordinates for a single voxel face were 0 -
	 * 1 on a given axis, they should now be 0 - width or 0 - height. Then you
	 * can calculate the correct texture coordinate in your fragement shader
	 * using coord.xy = fract(coord.xy).
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
	int lastId = 0;

	void quad(ModelBuilder builder, final Vector3 bottomLeft, final Vector3 topLeft, final Vector3 topRight,
			final Vector3 bottomRight, final int width, final int height, final VoxelFace voxel,
			final boolean backFace) {

		final Vector3[] vertices = new Vector3[4];

		vertices[0] = bottomLeft;// .scl(VOXEL_SIZE);
		vertices[1] = bottomRight;// .scl(VOXEL_SIZE);
		vertices[2] = topLeft;// .scl(VOXEL_SIZE);
		vertices[3] = topRight;// .scl(VOXEL_SIZE);

		final short[] indexes = backFace ? new short[] { 2, 3, 1, 1, 0, 2 } : new short[] { 2, 0, 1, 1, 3, 2 };

		Mesh quad = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 3, "a_position"),
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));

		quad.setVertices(
				new float[] { vertices[0].x, vertices[0].y, vertices[0].z, vertices[1].x, vertices[1].y, vertices[1].z,
						vertices[2].x, vertices[2].y, vertices[2].z, vertices[3].x, vertices[3].y, vertices[3].z });

		quad.setIndices(indexes);

		if (voxel.type == 1)
			builder.part("face" + lastId, quad, GL20.GL_TRIANGLES,
					new Material(TextureAttribute.createDiffuse(texture)));
		else
			builder.part("face" + lastId, quad, GL20.GL_TRIANGLES,
					new Material(TextureAttribute.createDiffuse(notexture)));
		lastId++;
	}

	@Override
	public ModelInstance getModelInstance() {
		return modelInstance;
	}

}
