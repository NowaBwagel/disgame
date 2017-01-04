package com.nowabwagel.disgame.terrain;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Terrain implements RenderableProvider {
	// FIXME DISPOSIBLE THINGYS NEED TO BE DISTROYED RIGHTFULLY!!!!!!!!!!!!!!!

	// TODO: Change to arraylist maybe, will need to change when on-demand
	// loading world chunks

	// private List<TerrainChunk> chunks;
	// private int loadX;
	// private int loadZ;

	public final TerrainChunk[] chunks;
	public final boolean[] dirty;
	public float tilesX;
	public float tilesZ;

	public int indOffset = 0;

	public int numTiles;
	public int renderedTiles;

	// public final TextureRegion texTile;

	public Terrain(int tilesX, int tilesZ) {

		this.chunks = new TerrainChunk[tilesX * tilesZ];
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

		this.dirty = new boolean[tilesX * tilesZ];
		for (i = 0; i < dirty.length; i++)
			dirty[i] = true;
	}

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {

		// Set mesh world trasform matrix with terrain x, z
		renderedTiles = 0;
		for (int i = 0; i < chunks.length; i++) {
			TerrainChunk chunk = chunks[i];
			Mesh mesh = chunk.getMesh();

			if (dirty[i]) {
				mesh.setVertices(chunk.getVertices());
				mesh.setIndices(chunk.getIndices());
				dirty[i] = false;
			}

			Renderable renderable = pool.obtain();
			renderable.worldTransform.setTranslation(chunk.getX(), 0, chunk.getZ());

			renderable.material = chunk.getMaterial();
			renderable.meshPart.mesh = mesh;
			renderable.meshPart.offset = 0;
			renderable.meshPart.size = chunk.getIndices().length;
			renderable.meshPart.primitiveType = GL20.GL_TRIANGLES;
			renderables.add(renderable);
			renderedTiles++;
		}
	}

}
