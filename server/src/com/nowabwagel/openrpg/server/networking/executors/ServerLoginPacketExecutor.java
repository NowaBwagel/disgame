package com.nowabwagel.openrpg.server.networking.executors;

import com.jmr.wrapper.common.Connection;
import com.nowabwagel.openrpg.server.ServerOpenRPG;

import old.common.networking.IPacketExecutor;
import old.common.networking.Packet;

public class ServerLoginPacketExecutor implements IPacketExecutor {

	private ServerOpenRPG serverOpenRPG;

	public ServerLoginPacketExecutor(ServerOpenRPG serverOpenRPG) {
		this.serverOpenRPG = serverOpenRPG;
	}

	@Override
	public void execute(Connection sender, Packet packet) {
		// TODO Auto-generated method stub

	}

}
