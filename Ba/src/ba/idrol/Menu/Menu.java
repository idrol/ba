package ba.idrol.Menu;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

public class Menu extends GameComponent {
	
	Play playBtn;
	Exit exitBtn;
	GameObject bg, logo;
	
	@Override
	public void loadObjects(){
		bg = new GameObject(800, 600, new Sprite("/res/images/world/bg.png"), 0, 0);
		logo = new GameObject(512, 128, new Sprite("res/images/menu/logo.png"), 190, 420);
		playBtn = new Play(new Sprite("/res/images/menu/play.png"), 270, 350);
		exitBtn = new Exit(new Sprite("/res/images/menu/exit.png"), 270, 200);
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
