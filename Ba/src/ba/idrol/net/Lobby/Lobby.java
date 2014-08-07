package ba.idrol.net.Lobby;

import org.newdawn.slick.Color;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;

public class Lobby extends GameComponent{
	private GameObject inv, chat;
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
}
