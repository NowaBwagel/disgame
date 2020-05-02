package com.nowabwagel.openrpg.common.registrys;

import java.util.HashMap;
import java.util.Map;

import com.nowabwagel.openrpg.common.world.terrain.block.Block;

public class BlockRegistry implements GameRegistry<Block> {

	private Map<String, Block> blocks;

	public BlockRegistry() {
		blocks = new HashMap<>();
	}

	@Override
	public boolean register(String idenifier, Block object) {
		if (blocks.containsKey(idenifier)) {
			return false;
		}
		blocks.put(idenifier, object);
		return true;

	}

}
