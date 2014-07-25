package ba.idrol.Game;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

public class Game extends GameComponent {

	GameObject bg;
	GameObject plr;
	GameObject ground, platform_1, platform_2, platform_3, platform_4, platform_5, platform_6, platform_7, platform_8;
	public Game(){
		
	}
	@Override
	public void loadObjects(){
		bg = new GameObject(800, 600, new Sprite("/res/images/world/bg.png"), 0, 0).disableCollision();
		plr = new Player(new Sprite("/res/images/character/char.png"), 30, 50).enableGravity();
		ground = new GameObject(800, 10, new Sprite("/res/images/world/sand.png"), 0, 0);
		Sprite platform = new Sprite("/res/images/world/platform.png");
		platform_1 = new GameObject(platform, 100, 60);
		platform_2 = new GameObject(platform, 400, 100);
		platform_3 = new GameObject(platform, 600, 60);
		platform_4 = new GameObject(platform, 500, 160);
		platform_5 = new GameObject(platform, 310, 260);
		platform_6 = new GameObject(platform, 120, 360);
		platform_7 = new GameObject(platform, 290, 460);
		platform_8 = new GameObject(platform, 400, 560);
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
