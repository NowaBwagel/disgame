package com.nowabwagel.openrpg.world.block;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nowabwagel.openrpg.Assets;
import com.nowabwagel.openrpg.registry.GameRegistrys;;

//TODO:Move to someone inside rendering maybe as it handles holding texture regions.
public class BlockManager {
	public static BlockManager instance;

	TextureAtlas atlas;
	Map<String, AtlasRegion> regions;
	Map<Integer, Block> blockIDMap;

	public BlockManager() {
		Assets.instance.getAssetManager().load("Textures/Blocks", TextureAtlas.class);
		Assets.instance.getAssetManager().finishLoading();
		atlas = Assets.instance.getAssetManager().get("Textures/Blocks", TextureAtlas.class);
		regions = new HashMap<>();
		blockIDMap = new HashMap<>();
		for (AtlasRegion temp : atlas.getRegions()) {
			// Filter for registered blocks
			regions.put(temp.name, temp);
		}
		int i = 0;
		for (Block b : GameRegistrys.BLOCK_REGISTRY.getBlocks().values()) {
			blockIDMap.put(i, b);
			b.setManagerID(i);
			i++;
		}
	}

	public AtlasRegion getRegion(Block block) {
		return regions.get(block.getRegionName());
	}

	public TextureRegion getRegion(int type) {
		return regions.get(blockIDMap.get(type).getRegionName());
	}
}
