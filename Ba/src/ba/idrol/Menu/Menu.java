package ba.idrol.Menu;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

public class Menu extends GameComponent {
	
	Play playBtn;
	Exit exitBtn;
	
	@Override
	public void loadObjects(){
		playBtn = new Play(new Sprite("/res/images/menu/play.png"), 270, 400);
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
