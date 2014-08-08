package ba.idrol.net.Lobby;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.newdawn.slick.Color;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.util.MD5;

public class Lobby extends GameComponent{
	private GameObject inv, chat;
	private LobbyNetwork lobbyNetwork;
	@Override
	public void loadObjects(){
		inv = new MenuItem(10, 10, 350, 400, Color.gray);
		chat = new MenuItem(10, 420, 450, 170, Color.gray);
	}
	
	@Override
	public void render(){
		for(GameObject obj: objList){
			obj.render();
		}
	}

	public GameComponent connect() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		lobbyNetwork = new LobbyNetwork();
		lobbyNetwork.connect(Main.currentUser.userName, MD5.generateMD5(Main.currentUser.userName+"+"+Main.currentUser.salt));
		return this;
	}
}
