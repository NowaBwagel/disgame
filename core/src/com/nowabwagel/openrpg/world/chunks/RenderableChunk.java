package com.nowabwagel.openrpg.world.chunks;

import com.badlogic.gdx.graphics.Mesh;

public interface RenderableChunk {
	
	void updateMesh();

	boolean isDirty();
	
	void setDirty(boolean dirty);
	
	void setMesh(Mesh mesh);
	
	boolean hasMesh();
	
	Mesh getMesh();
	
	void disposeMesh();
	
	boolean isReady();
	
	void markReady();
}
