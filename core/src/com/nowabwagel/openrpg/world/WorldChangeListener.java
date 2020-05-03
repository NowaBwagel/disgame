package com.nowabwagel.openrpg.world;

import com.badlogic.gdx.math.Vector3;
import com.nowabwagel.openrpg.world.block.Block;

public interface WorldChangeListener {

	void onBlockChanged(Vector3 pos, Block newBlock, Block originalBlock);
	
}
