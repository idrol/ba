package ba.idrol.Menu;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

public class Menu extends GameComponent {
	
	Play playBtn;
	Exit exitBtn;
	GameObject bg, logo, cloud_1, cloud_2, cloud_3, cloud_4, cloud_5, cloud_6, cloud_7, cloud_8, cloud_9, cloud_10;
	
	@Override
	public void loadObjects(){
		Sprite cloud = new Sprite("/res/images/world/cloud.png");
		bg = new GameObject(800, 600, new Sprite("/res/images/world/bg.png"), 0, 0);
		
		cloud_1 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_2 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_3 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_4 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_5 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_6 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_7 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_8 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_9 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_10 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		
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
