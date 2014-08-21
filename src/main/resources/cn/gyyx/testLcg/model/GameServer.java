package cn.gyyx.testLcg.model;

public class GameServer {
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getGame_id() {
		return game_id;
	}
	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}
	public String getGame_server() {
		return game_server;
	}
	public void setGame_server(String game_server) {
		this.game_server = game_server;
	}
	public int code;
	public String game_id;
	public String game_server;
	@Override
	public String toString() {
		return "GameServer [code=" + code + ", game_id=" + game_id
				+ ", game_server=" + game_server + "]";
	}
}
