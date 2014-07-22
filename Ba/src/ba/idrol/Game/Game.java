package ba.idrol.Game;

import java.io.IOException;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

public class Game extends GameComponent {

	GameObject plr;
	GameObject ground, platform_1, platform_2, platform_3;
	public Game(){
		
	}
	@Override
	public void loadObjects(){
		plr = new Player(new Sprite("/res/images/character/char.png"), 30, 50).enableGravity();
		ground = new GameObject(800, 10, new Sprite("/res/images/world/sand.png"), 0, 0);
		Sprite platform = new Sprite("/res/images/world/platform.png");
		platform_1 = new GameObject(platform, 100, 60);
		platform_2 = new GameObject(platform, 400, 100);
		platform_3 = new GameObject(platform, 600, 60);
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
}
