package com.nowabwagel.openrpg.app;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.GridPoint3;
import com.nowabwagel.openrpg.GameConfig;
import com.nowabwagel.openrpg.rendering.RenderingSystem;
import com.nowabwagel.openrpg.world.block.BlockManager;
import com.nowabwagel.openrpg.world.chunks.internal.ChunkImpl;

public class OpenRPG extends Game {

	private Engine engine; // Ashley entity

	private RenderingSystem renderingSystem;

	private ModelBatch batch;
	private PerspectiveCamera cam;

	@Override
	public void create() {
		engine = new Engine();

		if (GameConfig.isClient) {
			batch = new ModelBatch();
			cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			renderingSystem = new RenderingSystem(batch, cam);
			BlockManager.instance = new BlockManager();
			
			engine.addSystem(renderingSystem);
		}
		
		ChunkImpl chunk = new ChunkImpl(new GridPoint3());
		// chunk.

	}

}
