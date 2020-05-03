package com.nowabwagel.openrpg.world.internal;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint3;
import com.nowabwagel.openrpg.world.WorldChangeListener;
import com.nowabwagel.openrpg.world.WorldProvider;
import com.nowabwagel.openrpg.world.block.Block;
import com.nowabwagel.openrpg.world.chunks.ChunkProvider;
import com.nowabwagel.openrpg.world.time.WorldTime;
import com.nowabwagel.openrpg.world.time.WorldTimeImpl;

public class WorldProviderImpl implements WorldProvider {

	private String name;
	private String seed = "";

	private ChunkProvider chunkProvier;
	private WorldTime worldTime;
	// TODO: EntityManager

	private final List<WorldChangeListener> listeners = new ArrayList<>();

	public WorldProviderImpl(String name, String seed, long time) {
		this.name = name;
		this.seed = seed;
		this.worldTime = new WorldTimeImpl();
		worldTime.setMilliseconds(time);
	}

	@Override
	public Entity getWorldEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSeed() {
		// TODO Auto-generated method stub
		return seed;
	}

	@Override
	public void registerListener(WorldChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void deregisterListener(WorldChangeListener listener) {
		listeners.remove(listener);
	}

	@Override
	public boolean isBlockLoaded(int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block setBlock(GridPoint3 pos, Block type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorldTime getTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
