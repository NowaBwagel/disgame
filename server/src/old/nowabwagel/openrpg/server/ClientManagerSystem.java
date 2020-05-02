package old.nowabwagel.openrpg.server;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.systems.IntervalSystem;
import com.jmr.wrapper.common.Connection;

public class ClientManagerSystem extends IntervalSystem {
	public static List<Connection> connections;

	public ClientManagerSystem() {
		super(0.5f);
		connections = new ArrayList<Connection>();
	}

	public static void addClient(Connection con) {
		System.out.println("new client");
		connections.add(con);
	}

	@Override
	protected void updateInterval() {

	}

}
