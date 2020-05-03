package old.common.entity.components;

import com.badlogic.ashley.core.Component;

/**
 * Allow Entity to make a chunk to be active.
 * 
 * @author noahb
 *
 */
public class ChunkActivityFlagComponent implements Component{

	private boolean enabled;

	public ChunkActivityFlagComponent(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
