package com.nowabwagel.openrpg.common.world.terrain;

import java.util.Map;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Terrain implements RenderableProvider {
	
	//TODO: ref for BlockMaterialRegistry
	
	//GridPoint2 is 2d int coordinates.
	public Map<GridPoint2, TerrainChunk> chunks;

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		
	}
}
