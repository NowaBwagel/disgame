package old.common.world;

import com.badlogic.ashley.core.Engine;

public abstract class AbstractOpenRPGWorld {
	/**
	 * Entity Engine
	 */
	private Engine engine;

	// TODO:Implement Chunking system.

	public Engine getEngine() {
		return engine;
	}

}
