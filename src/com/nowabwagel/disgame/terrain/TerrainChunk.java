package com.nowabwagel.disgame.terrain;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Matrix4;

public class TerrainChunk {
	// FIXME: encapsulate class

	public static final int VERTEX_SIZE = 3 + 3 + 2;

	private float[] vertices;

	private final short[] indices;
	private final float verticeWidth;
	private final short widthCount;
	private final float x;
	private final float z;

	private Mesh mesh;
	private Material material;
	private List<ModelInstance> worldMeshes;
	private Matrix4 transformMatrix;

	// public final float vertexOffset;
	// public final short widthCount;
	// public final float x;
	// public final float z;
	// public final short[] indices;

	// public Matrix4 transformMatrix;

	// public float[] vertices;

	public TerrainChunk(float verticeWidth, short widthCount, float x, float z) {

		this.verticeWidth = verticeWidth;
		this.widthCount = widthCount;
		this.x = x * verticeWidth * (widthCount - 1);
		this.z = z * verticeWidth * (widthCount - 1);

		this.indices = new short[widthCount * widthCount * 6];
		this.vertices = new float[widthCount * widthCount * (VERTEX_SIZE)];

		mesh = new Mesh(false, widthCount * widthCount * 3, widthCount * widthCount * 6, VertexAttribute.Position(),
				VertexAttribute.Normal(), VertexAttribute.TexCoords(0));

		Texture texture = new Texture(Gdx.files.internal("assets/grass.jpg"), true);
		texture.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestLinear);

		material = new Material(new TextureAttribute(TextureAttribute.createDiffuse(texture)));

		buildIndices();
		buildFlatVertices();

		mesh.setIndices(indices);
		mesh.setVertices(vertices);
	}

	/*
	 * I1: Upper Left I2: Upper Right I3: Lower RIght I4: Lower Left
	 */
	public void buildIndices() {
		int idx = 0;
		short i1 = 0;
		short i2 = 1;
		short i3 = (short) (widthCount + 1);
		short i4 = (short) (widthCount);

		for (int z = 0; z < widthCount - 1; z++) {
			for (int x = 0; x < widthCount - 1; x++) {
				indices[idx] = i2;
				indices[idx + 1] = i1;
				indices[idx + 2] = i4;
				idx += 3;

				indices[idx] = i4;
				indices[idx + 1] = i3;
				indices[idx + 2] = i2;
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
		try {
			for (int z = 0; z < widthCount; z++) {
				for (int x = 0; x < widthCount; x++) {
					vertices[idx] = x * verticeWidth;
					vertices[idx + 1] = x * z;
					vertices[idx + 2] = z * verticeWidth;
					idx += 3;

					// ADD Normals - Staight up
					vertices[idx] = 0;
					vertices[idx + 1] = 1f;
					vertices[idx + 2] = 0;
					idx += 3;

					// ADD TexCoords * Scale
					vertices[idx] = (float) x / (widthCount - 1) * 2f;
					vertices[idx + 1] = (float) z / (widthCount - 1) * 2f;
					idx += 2;
				}

			}
		} catch (Exception e) {
			System.out.println("AHHHHHH");
			System.exit(1);
		}
	}

	public List<ModelInstance> getWorldMeshes() {
		return worldMeshes;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Matrix4 getTransformMatrix() {
		return transformMatrix;
	}

	public void setTransformMatrix(Matrix4 transformMatrix) {
		this.transformMatrix = transformMatrix;
	}

	public short[] getIndices() {
		return indices;
	}

	public float getVerticeWidth() {
		return verticeWidth;
	}

	public short getWidthCount() {
		return widthCount;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

}
