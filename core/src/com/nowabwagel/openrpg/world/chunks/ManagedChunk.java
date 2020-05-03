package com.nowabwagel.openrpg.world.chunks;

import com.badlogic.gdx.utils.Disposable;

public interface ManagedChunk extends Chunk, Disposable {

	void markReady();

	boolean isReady();
	
	boolean isDisposed();
	
}
