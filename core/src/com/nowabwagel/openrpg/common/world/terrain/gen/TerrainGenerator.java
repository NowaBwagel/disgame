package com.nowabwagel.openrpg.common.world.terrain.gen;

import com.nowabwagel.openrpg.common.world.terrain.block.Block;

public abstract class TerrainGenerator {

	public abstract Block generate(int x, int y, int z);
}
