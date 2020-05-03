package com.nowabwagel.openrpg.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint3;
import com.nowabwagel.openrpg.world.block.Block;
import com.nowabwagel.openrpg.world.time.WorldTime;

public interface WorldProvider{

	Entity getWorldEntity();

	String getName();

	String getSeed();

	void registerListener(WorldChangeListener listener);

	void deregisterListener(WorldChangeListener listener);

	boolean isBlockLoaded(int x, int y, int z);

	/**
	 * Sets block type at a given position.
	 * 
	 * @param pos  The world position to change
	 * @param type The block type to set
	 * @return The previous block type. Null if block failed to set (unloaded)
	 */
	Block setBlock(GridPoint3 pos, Block type);

	Block getBlock(int x, int y, int z);
	
	WorldTime getTime();
}
