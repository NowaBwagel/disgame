package com.nowabwagel.disgame.terrain;

public class TerrainTile {
	// TODO: Merge exact tiles together
	public static final int TILE_SIZE = 10;
	public static final int VERTEX_COUNT = 3;

	public float[] vertices;
	// public float[] normals;
	// public float[] textureCoords;

	public float x;
	public float z;

	public TerrainTile(int gridX, int gridZ) {
		x = TILE_SIZE * gridX;
		z = TILE_SIZE * gridZ;

		int count = VERTEX_COUNT * VERTEX_COUNT;
		vertices = new float[count * 3];
		// normals = new float[count * 3];
		// textureCoords = new float[count * 2];

		int vertexPointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT; gx++) {
				// vertices[vertexPointer * 3]

				vertices[vertexPointer * 3] = ((float) gx / (VERTEX_COUNT) * TILE_SIZE);
				vertices[vertexPointer * 3 + 1] = 0;
				vertices[vertexPointer * 3 + 2] = ((float) gz / (VERTEX_COUNT) * TILE_SIZE);
				// normals[vertexPointer * 3] = 0;
				// normals[vertexPointer * 3 + 1] = 1;
				// normals[vertexPointer * 3 + 2] = 0;
				// textureCoords[vertexPointer * 2] = (float) j / ((float)
				// VERTEX_COUNT - 1);
				// textureCoords[vertexPointer * 2 + 1] = (float) i / ((float)
				// VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		System.out.println(vertices.length);
	}
}
