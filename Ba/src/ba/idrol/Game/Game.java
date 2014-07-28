package ba.idrol.Game;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.minlog.Log;

import ba.idrol.Menu.Menu;
import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;
import ba.idrol.network.client.Network;

public class Game extends GameComponent {
	public static Map<Integer, MpPlayer> players = new HashMap<Integer, MpPlayer>();
	GameObject bg;
	public static GameObject plr;
	GameObject ground, platform_1, platform_2, platform_3, platform_4, platform_5, platform_6, platform_7, platform_8, cloud_1, cloud_2, cloud_3, cloud_4, cloud_5, cloud_6, cloud_7, cloud_8, cloud_9, cloud_10;
	GameObject sw_1, sw_2, sh_1, sh_2;
	private static Network network;
	public Game(){
		network = new Network();
	}
	public static Network getNetwork(){
		return network;
	}
	@Override
	public void loadObjects(){
		world();
		Sprite sword = new Sprite("/res/images/items/sword.png");
		Sprite shield = new Sprite("/res/images/items/shield.png");

		sw_1 = new GameObject(sword, 310, 700).enableGravity().disableCollision();
		sw_2 = new GameObject(sword, 410, 700).enableGravity().disableCollision();
		sh_1 = new GameObject(shield, 600, 700).enableGravity().disableCollision();
		sh_2 = new GameObject(shield, 200, 700).enableGravity().disableCollision();
		plr = new LocalPlayer(new Sprite("/res/images/character/char.png"), 30, 50);
		network.connect();
	}
	@Override
	public void update(int delta){
		MpPlayer.createPlayers();
		for(GameObject obj: this.objList){
			obj.update();
			if(obj instanceof LocalPlayer){
				((LocalPlayer) obj).networkUpdate();
			}
		}
	}
	@Override
	public void render(){
		for(GameObject obj: this.objList){
			obj.render();
		}
	}
	public void world(){
		bg = new GameObject(800, 600, new Sprite("/res/images/world/bg.png"), 0, 0).disableCollision();
		ground = new GameObject(800, 10, new Sprite("/res/images/world/sand.png"), 0, 0);
		
		Sprite cloud = new Sprite("/res/images/world/cloud.png");
		Sprite platform = new Sprite("/res/images/world/platform.png");
		
		cloud_1 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_2 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_3 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_4 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_5 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_6 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_7 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_8 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_9 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		cloud_10 = new GameObject(cloud, Menu.randomGen(800), Menu.randomGen(600));
		
		platform_1 = new GameObject(platform, 100, 60);
		platform_2 = new GameObject(platform, 400, 100);
		platform_3 = new GameObject(platform, 600, 60);
		platform_4 = new GameObject(platform, 500, 160); 
		platform_5 = new GameObject(platform, 310, 260);
		platform_6 = new GameObject(platform, 120, 360);
		platform_7 = new GameObject(platform, 290, 460);
		platform_8 = new GameObject(platform, 400, 560);
	}
}
