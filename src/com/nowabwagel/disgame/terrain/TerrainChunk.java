package com.nowabwagel.disgame.terrain;

import com.badlogic.gdx.math.Matrix4;

public class TerrainChunk {
	// FIXME: encapsulate class
	public final float vertexOffset;
	public final short widthCount;
	public final int x;
	public final int z;
	public final short[] indices;

	public Matrix4 transformMatrix;

	public float[] vertices;

	public TerrainChunk(float vertexOffset, short widthCount, int x, int z) {
		this.vertexOffset = vertexOffset;
		this.widthCount = widthCount;
		this.x = x;
		this.z = z;

		this.indices = new short[widthCount * widthCount * 6];
		// TODO: Allow more vertex data by changing '3' into variable. 3:pos,
		// 3:normal, 2:texcoords
		this.vertices = new float[widthCount * widthCount * (3 + 3 + 2)];

		buildIndices();
		buildFlatVertices();
	}

	/*
	 * I1: Upper Left I2: Upper Right I3: Lower RIght I4: Lower Left
	 */
	public void buildIndices() {
		int idx = 0;
		System.out.println(widthCount + 1);
		short i1 = 0;
		short i2 = 1;
		short i3 = (short) (widthCount + 1);
		short i4 = (short) (widthCount);

		for (int z = 0; z < widthCount - 1; z++) {
			for (int x = 0; x < widthCount - 1; x++) {
				indices[idx] = i2;
				indices[idx + 1] = i1;
				indices[idx + 2] = i4;
				System.out.print("(" + indices[idx] + ", " + indices[idx + 1] + ", " + indices[idx + 2] + ")");
				idx += 3;

				indices[idx] = i4;
				indices[idx + 1] = i3;
				indices[idx + 2] = i2;
				System.out.println("(" + indices[idx] + ", " + indices[idx + 1] + ", " + indices[idx + 2] + ")");
				idx += 3;

				i1++;
				i2++;
				i3++;
				i4++;
			}

			i1++;
			i2++;
			i3++;
			i4++;
		}

	}

	public void buildFlatVertices() {

		int idx = 0;
		int inc = 3;
		try {
			for (int z = 0; z < widthCount; z++) {
				for (int x = 0; x < widthCount; x++) {
					vertices[idx] = x * vertexOffset;
					vertices[idx + 1] = 0;
					vertices[idx + 2] = z * vertexOffset;
					System.out.print("(" + vertices[idx] + ", " + vertices[idx + 1] + ", " + vertices[idx + 2] + ") ");
					idx += inc;

					// ADD Normals - Staight up
					vertices[idx] = 0;
					vertices[idx + 1] = 1f;
					vertices[idx + 2] = 0;

					System.out.print("(" + vertices[idx] + ", " + vertices[idx + 1] + ", " + vertices[idx + 2] + ") ");
					idx += 3;

					// ADD TexCoords * Scale
					vertices[idx] = (float) x / (widthCount - 1) * 2f;
					vertices[idx + 1] = (float) z / (widthCount - 1) * 2f;

					System.out.println("(" + vertices[idx] + ", " + vertices[idx + 1] + ")");
					idx += 2;
				}

			}
		} catch (Exception e) {
			System.out.println("AHHHHHH");
			System.exit(1);
		}
	}

}
