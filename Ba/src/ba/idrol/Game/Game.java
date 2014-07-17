package ba.idrol.Game;

import java.io.IOException;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

public class Game extends GameComponent {

	Player plr;
	GameObject obj;
	public Game(){
		
	}
	@Override
	public void loadObjects(){
		try {
			plr = new Player(new Sprite("/res/images/character/char.png"), 30, 50);
			obj = new GameObject(800, 10, new Sprite("/res/no_texture.png"), 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void update(int delta){
		for(GameObject obj: this.objList){
			obj.update();
		}
	}
	@Override
	public void render(){
		for(GameObject obj: this.objList){
			obj.render();
		}
	}
	public void deconstruct(){
		plr = null;
		obj = null;
	}
}
