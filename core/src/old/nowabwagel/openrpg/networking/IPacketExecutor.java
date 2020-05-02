package old.nowabwagel.openrpg.networking;

import com.jmr.wrapper.common.Connection;

public interface IPacketExecutor{
	/**
	 * Executors should know what order the data is and the type of each data and
	 * cast when needed.
	 * 
	 * @param sender
	 *            Who has send this data.
	 * @param data
	 *            All data as strings split at separator (:) includes packet
	 *            Identity in index 0.
	 */
	public void execute(Connection sender, Packet packet);
}
