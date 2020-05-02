package com.nowabwagel.openrpg.common.registrys;

import java.util.HashMap;
import java.util.Map;

import com.nowabwagel.openrpg.common.world.terrain.block.Block;
import com.nowabwagel.openrpg.common.world.terrain.block.BlockMaterial;

public class BlockMaterialRegistry implements GameRegistry<BlockMaterial> {

	// Indentifyer, Material -- opencore:stone/opencore:cobblestone
	private Map<String, BlockMaterial> blockMaterials;

	public BlockMaterialRegistry() {
		blockMaterials = new HashMap<>();
	}

	@Override
	public boolean register(String id, BlockMaterial blockMat) {
		boolean replaced = blockMaterials.containsKey(id);
		blockMaterials.put(id, blockMat);
		return replaced;
	}

	public BlockMaterial getMaterial(Block block) {
		BlockMaterial mat = blockMaterials.get(block.getKey());
		if (mat == null) {
			// mat = defaultMaterial
		}
		return mat;
	}
}
