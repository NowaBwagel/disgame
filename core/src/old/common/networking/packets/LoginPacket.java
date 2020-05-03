package old.common.networking.packets;

import old.common.networking.Packet;

public class LoginPacket extends Packet {
	private static final long serialVersionUID = 2545100771402090593L;

	public enum ResponceType {
		Login_Success, Login_Unregisted_Username, Login_Bad_Combo
	}

	private String login;
	private String password;

	private ResponceType responceType;
	private String sessionID;

	public LoginPacket(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public LoginPacket(ResponceType responce, String sessionID) {
		this.responceType = responce;
		this.sessionID = sessionID;
	}
}
