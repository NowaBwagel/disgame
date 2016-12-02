package com.nowabwagel.disgame.terrain;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Terrain implements RenderableProvider {
	// TODO: Change to arraylist maybe, will need to change when on-demand
	// loading world chunks
	public final TerrainChunk[] chunks;
	public final Mesh[] meshes;
	public final Material[] materials;
	public final boolean[] dirty;
	public float[] vertices;
	public short[] indices;
	public float tilesX;
	public float tilesZ;

	public int indOffset = 0;

	public int numTiles;
	public int renderedTiles;

	// public final TextureRegion texTile;

	public Terrain(int tilesX, int tilesZ) {// TextureRegion texTile, int
											// tilesX, int tilesZ) {
		this.chunks = new TerrainChunk[tilesX * tilesZ];
		// this.texTile = texTile;
		this.tilesX = tilesX;
		this.tilesZ = tilesZ;
		this.numTiles = tilesX * tilesZ;

		int i = 0;
		for (int x = 0; x < tilesX; x++) {
			for (int z = 0; z < tilesZ; z++) {
				TerrainChunk tile = new TerrainChunk(10, (short) 5, x, z);
				chunks[i++] = tile;
			}
		}

		vertices = chunks[0].vertices;
		indices = chunks[0].indices;

		this.meshes = new Mesh[tilesX * tilesZ];
		for (i = 0; i < meshes.length; i++) {
			meshes[i] = new Mesh(false, 5 * 5 * 3, 5 * 5 * 6 * 2, VertexAttribute.Position(),VertexAttribute.Normal());
			meshes[i].setIndices(indices);
		}

		this.dirty = new boolean[tilesX * tilesZ];
		for (i = 0; i < dirty.length; i++)
			dirty[i] = true;

		// this.vertices = new float[numVertices];
		this.materials = new Material[tilesX * tilesZ];

		for (i = 0; i < materials.length; i++) {
			materials[i] = new Material(new ColorAttribute(ColorAttribute.Diffuse, 0, 1, 0, 1));
		}
	}

	short[] testindices = { 0, 3, 1 };// , 1, 3, 4, 1, 4, 2, 2, 4, 5, 3, 6, 4,
										// 4, 6, 7, 4, 7, 5, 5, 7, 8 };

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {

		// Set mesh world trasform matrix with terrain x, z
		renderedTiles = 0;
		for (int i = 0; i < chunks.length; i++) {
			TerrainChunk chunk = chunks[i];
			Mesh mesh = meshes[i];

			if (dirty[i]) {
				mesh.setVertices(chunk.vertices);
				mesh.setIndices(chunk.indices, 0, chunk.indices.length);
				dirty[i] = false;
			}

			Renderable renderable = pool.obtain();
			renderable.worldTransform.setTranslation(chunk.x, 0, chunk.z);

			renderable.material = materials[i];
			renderable.meshPart.mesh = mesh;
			renderable.meshPart.offset = 0;
			renderable.meshPart.size = chunk.indices.length;
			renderable.meshPart.primitiveType = GL20.GL_TRIANGLES;
			renderables.add(renderable);
			renderedTiles++;

			vertices = chunk.vertices;
		}
	}

}
