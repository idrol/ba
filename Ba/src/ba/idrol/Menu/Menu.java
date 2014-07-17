package ba.idrol.Menu;

import java.io.IOException;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

public class Menu extends GameComponent {
	
	Play playBtn;
	
	@Override
	public void loadObjects(){
		try {
			playBtn = new Play(new Sprite("/res/menu/play.png"), 300, 450);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(int delta){
		for(GameObject obj: objList){
			obj.update();
		}
	}
	
	@Override
	public void render(){
		for(GameObject obj: objList){
			obj.render();
		}
	}
}
