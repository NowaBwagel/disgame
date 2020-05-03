package com.nowabwagel.openrpg.world.chunks;

import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.math.Vector3;
import com.nowabwagel.openrpg.world.block.Block;

public interface Chunk{

	GridPoint3 getPosition();

	Block getBlock(GridPoint3 pos);

	Block getBlock(int x, int y, int z);

	Block setBlock(GridPoint3 pos, Block type);

	Block setBlock(int x, int y, int z, Block type);

	Vector3 getChunkOffset();

	int getChunkOffsetX();

	int getChunkOffsetY();

	int getChunkOffsetZ();

	GridPoint3 chunkPosToWorldPos(GridPoint3 blockPos);

	GridPoint3 chunkPosToWorldPos(int x, int y, int z);

	int getChunkSizeX();

	int getChunkSizeY();

	int getChunkSizeZ();
}
