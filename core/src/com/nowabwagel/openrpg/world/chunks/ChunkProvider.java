package com.nowabwagel.openrpg.world.chunks;

import com.badlogic.gdx.math.GridPoint3;

public interface ChunkProvider {

	boolean reloadChunk(GridPoint3 pos);
	
	/*
	 * Regenerate all chunks
	 */
	void purgeWorld();
	
	boolean isChunkLoaded(GridPoint3 pos);
	
}
