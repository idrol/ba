package ba.idrol.server;

public class World {
	GameObject bg, ground, platform_1, platform_2, platform_3, platform_4, platform_5, platform_6, platform_7, platform_8, cloud_1, cloud_2, cloud_3, cloud_4, cloud_5, cloud_6, cloud_7, cloud_8, cloud_9, cloud_10;
	GameObject sw_1, sw_2, sh_1, sh_2;
	public World(){
		ground = new GameObject(800, 10, 0, 0, "ground");
		platform_1 = new GameObject(64, 4, 100, 60, "platform");
		platform_2 = new GameObject(64, 4, 400, 100, "platform");
		platform_3 = new GameObject(64, 4, 600, 60, "platform");
		platform_4 = new GameObject(64, 4, 500, 160, "platform"); 
		platform_5 = new GameObject(64, 4, 310, 260, "platform");
		platform_6 = new GameObject(64, 4, 120, 360, "platform");
		platform_7 = new GameObject(64, 4, 290, 460, "platform");
		platform_8 = new GameObject(64, 4, 400, 560, "platform");
		
		sw_1 = new GameObject(16, 16, 310, 700, "sword").enableGravity().disableCollision();
		sw_2 = new GameObject(16, 16, 410, 700, "sword").enableGravity().disableCollision();
		sh_1 = new GameObject(16, 16, 600, 700, "shield").enableGravity().disableCollision();
		sh_2 = new GameObject(16, 16, 200, 700, "shield").enableGravity().disableCollision();
	}
}
