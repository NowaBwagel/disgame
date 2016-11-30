package com.nowabwagel.disgame.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class WorldTerrain implements RenderableProvider {
	// TODO: Change to arraylist maybe, will need to change when on-demand
	// loading world chunks
	public final TerrainTile[] tiles;
	public final Mesh[] meshes;
	public final Material[] materials;
	public final boolean[] dirty;
	public final int numVertices;
	public float[] vertices;
	public short[] indices;
	public float tilesX;
	public float tilesZ;

	public int indOffset = 0;

	public int numTiles;
	public int renderedTiles;

	// public final TextureRegion texTile;

	public WorldTerrain(int tilesX, int tilesZ) {// TextureRegion texTile, int
													// tilesX, int tilesZ) {
		this.tiles = new TerrainTile[tilesX * tilesZ];
		// this.texTile = texTile;
		this.tilesX = tilesX;
		this.tilesZ = tilesZ;
		this.numTiles = tilesX * tilesZ;

		this.numVertices = TerrainTile.VERTEX_COUNT * TerrainTile.VERTEX_COUNT * 3;

		int i = 0;
		for (int x = 0; x < tilesX; x++) {
			for (int z = 0; z < tilesZ; z++) {
				TerrainTile tile = new TerrainTile(x, z);
				tiles[i++] = tile;
			}
		}

		vertices = tiles[0].vertices;

		// Indices are the same for every mesh so moved to WorldTerrain so they
		// arn't calculated and wasting memory every new tile loaded.

		indices = new short[6 * (TerrainTile.VERTEX_COUNT - 1) * (TerrainTile.VERTEX_COUNT - 1)];
		int pointer = 0;
		for (int x = 0; x < TerrainTile.VERTEX_COUNT - 1; x++) {
			for (int z = 0; z < TerrainTile.VERTEX_COUNT - 1; z++) {
				indices[pointer + 0] = (short) (z * TerrainTile.VERTEX_COUNT + x);
				indices[pointer + 1] = (short) (indices[pointer] + TerrainTile.VERTEX_COUNT);
				indices[pointer + 2] = (short) (indices[pointer] + 1);
				indices[pointer + 3] = indices[pointer + 2];
				indices[pointer + 4] = indices[pointer + 1];
				indices[pointer + 5] = (short) (indices[pointer + 1] + 1);
				pointer += 6;
			}

		}

		// int pointer = 0;
		// for (int gz = 0; gz < TerrainTile.VERTEX_COUNT - 1; gz++) {
		// for (int gx = 0; gx < TerrainTile.VERTEX_COUNT - 1; gx++) {
		// int topLeft = (gz * TerrainTile.VERTEX_COUNT) + gx;
		// int topRight = topLeft + 1;
		// int bottomLeft = ((gz + 1) * TerrainTile.VERTEX_COUNT) + gx;
		// int bottomRight = bottomLeft + 1;
		// indices[pointer++] = (short) topLeft;
		// indices[pointer++] = (short) bottomLeft;
		// indices[pointer++] = (short) topRight;
		// indices[pointer++] = (short) topRight;
		// indices[pointer++] = (short) bottomLeft;
		// indices[pointer++] = (short) bottomRight;
		// }
		// }

		this.meshes = new Mesh[tilesX * tilesZ];
		for (i = 0; i < meshes.length; i++) {
			meshes[i] = new Mesh(false, numVertices, indices.length, VertexAttribute.Position(),
					VertexAttribute.Normal());// VertexAttribute.Normal());
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

	short[] testindices = { 0, 3, 1};//, 1, 3, 4, 1, 4, 2, 2, 4, 5, 3, 6, 4, 4, 6, 7, 4, 7, 5, 5, 7, 8 };

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {

		// Set mesh world trasform matrix with terrain x, z
		renderedTiles = 0;
		for (int i = 0; i < tiles.length; i++) {
			TerrainTile tile = tiles[i];
			Mesh mesh = meshes[i];

			if (dirty[i]) {
				mesh.setVertices(tile.vertices);
				mesh.setIndices(testindices);
				dirty[i] = false;
			}

			Renderable renderable = pool.obtain();
			renderable.worldTransform.setTranslation(tile.x, 0, tile.z);

			renderable.material = materials[i];
			renderable.meshPart.mesh = mesh;
			renderable.meshPart.offset = 0;
			renderable.meshPart.size = numVertices;
			renderable.meshPart.primitiveType = GL20.GL_TRIANGLES;
			renderables.add(renderable);
			renderedTiles++;

			vertices = tile.vertices;
		}
	}

}
