package com.nowabwagel.voxel.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.nowabwagel.voxel.world.voxels.Block;

public class World implements Disposable {

	public static final int CHUNK_SIZE = 16;

	/*
	 * North - 0 | East - 1 | South - 2 | West - 3 | Up - 4 | Down - 5
	 */
	public enum Direction {
		NORTH, EAST, SOUTH, WEST, UP, DOWN
	}

	private Environment environment;
	private DirectionalLight sun;
	private Vector3 sunRot = new Vector3(1f, 0f, 0f);

	// FIXME: Make worldEntities
	// FIXME: Clean this up a bit
	private List<WorldRenderable> worldRenderables;
	private List<ModelInstance> worldModelInstances;

	private Map<Vector3, Chunk> chunks;
	private int changeIndex = 0;
	private int lastUpdateIndex = -1;

	public World() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.1f, 0.1f, 0.1f));
		sun = new DirectionalLight().set(Color.WHITE, new Vector3(0f, 0f, -1f));
		environment.add(sun);

		worldRenderables = new ArrayList<WorldRenderable>();
		worldModelInstances = new ArrayList<ModelInstance>();
		chunks = new HashMap<Vector3, Chunk>();

		// worldRenderables.add(c);
	}

	public void set(float x, float y, float z, Block type) {
		int ix = (int) x;
		int iy = (int) y;
		int iz = (int) z;
		int chunkX = ix / CHUNK_SIZE;
		int chunkY = iy / CHUNK_SIZE;
		int chunkZ = iz / CHUNK_SIZE;
		Chunk chunk;
		Vector3 chunkPos = new Vector3(chunkX, chunkY, chunkZ);
		if (!chunks.containsKey(chunkPos)) {
			// TODO: Check to see if the chunk is saved
			chunk = new Chunk(CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, chunkPos);
			chunks.put(chunkPos, chunk);
		} else
			chunk = chunks.get(chunkPos);

		chunk.setFast(ix % CHUNK_SIZE, iy % CHUNK_SIZE, iz % CHUNK_SIZE, type);

		changeIndex += 1;
	}

	float time;

	public void update() {
		// time += Gdx.graphics.getDeltaTime();
		// if (time >= 1 / 20f) {
		// sun.direction.rotate(sunRot, 360 * time);
		//
		// time = 0;
		// }
		sun.direction.rotate(sunRot, Gdx.graphics.getDeltaTime());
		if (sun.direction.y > -0.01)
			sun.direction.y = -1f;

		if (lastUpdateIndex != changeIndex) {
			worldModelInstances.clear();

			for (WorldRenderable r : worldRenderables)
				worldModelInstances.add(r.getModelInstance());

			for (Chunk c : chunks.values()) {
				worldModelInstances.add(c.getModelInstance());
			}
			lastUpdateIndex = changeIndex;
		}
	}

	public Environment getEnvironment() {
		return environment;
	}

	public DirectionalLight getSun() {
		return sun;
	}

	@Override
	public void dispose() {
	}

	public List<ModelInstance> getWorldModels() {
		return worldModelInstances;
	}

}
