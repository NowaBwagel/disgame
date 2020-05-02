/**
 * 
 */
package com.nowabwagel.openrpg.common.networking;

import com.jmr.wrapper.common.Connection;

/**
 * @author Noah Bergl
 *
 */
public interface IPacketExecutor {

	public void execute(Connection sender, Packet packet);

}
