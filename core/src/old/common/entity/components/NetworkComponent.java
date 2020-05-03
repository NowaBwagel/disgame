package old.common.entity.components;

import com.badlogic.ashley.core.Component;

public class NetworkComponent implements Component {

	private int id;

	// TODO: Implement update Ownership
	private boolean owner;

	public NetworkComponent(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
}
