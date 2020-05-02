package old.nowabwagel.openrpg.entity.components;

import com.badlogic.ashley.core.Component;

public class NetworkComponent implements Component {
	public int ID;
	// TODO: Implement Entity Ownership
	public boolean owner;

	public NetworkComponent(int id) {
		this.ID = id;
	}
}
