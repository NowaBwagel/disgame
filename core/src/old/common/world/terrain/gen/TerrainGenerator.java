package old.common.world.terrain.gen;

import old.common.world.terrain.block.Block;

public abstract class TerrainGenerator {

	public abstract Block generate(int x, int y, int z);
}
