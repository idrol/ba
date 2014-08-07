package ba.idrol.net.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.esotericsoftware.minlog.Log;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Sprites;
import ba.idrol.net.Menu.Menu;
import ba.idrol.net.client.MpPlayerData;
import ba.idrol.net.client.Network;
import ba.idrol.net.packets.PacketAddGameObject;
import ba.idrol.net.packets.PacketAddPlayer;
import ba.idrol.net.packets.PacketPlayerKeyPress;
import ba.idrol.net.packets.PacketPositionUpdatePlayer;
import ba.idrol.net.packets.PacketRemovePlayer;

/*
 * Playable game itself stored in a GameComponent.
 */
public class Game extends GameComponent {
	// Stores all non local players
	public static ConcurrentMap<Integer, MpPlayer> players = new ConcurrentHashMap<Integer, MpPlayer>();
	// Stores the local player
	public static GameObject plr;
	// Stores local game objects that dont need networking for example only graphical features.
	private GameObject bg, cloud_1, cloud_2, cloud_3, cloud_4, cloud_5, cloud_6, cloud_7, cloud_8, cloud_9, cloud_10;
	// Stores the object that holdes the network connection.
	private static Network network;
	
	/*
	 * Create the network connection object.
	 */
	public Game(String name){
		network = new Network(name);
	}
	
	/*
	 * Returns the network connection object.
	 */
	public static Network getNetwork(){
		return network;
	}
	
	/*
	 * @see ba.idrol.net.GameComponent#loadObjects()
	 * 
	 * Loads in sprites, creates the local player and connects to the server.
	 * @TODO: Add so that you are returned to the main menu if you cant connect.
	 */
	@Override
	public void loadObjects(){
		world();
		Sprites.addSprite(new Sprite("/res/images/items/sword.png"), "sword");
		Sprites.addSprite(new Sprite("/res/images/items/shield.png"), "shield");
		Sprites.addSprite(new Sprite("/res/images/character/char.png"), "player");
		Sprites.addSprite(new Sprite("/res/images/world/platform.png"), "platform");
		Sprites.addSprite(new Sprite("/res/images/world/sand.png"), "ground");
		Sprites.addSprite(new Sprite("/res/images/items/SwordH.png"), "plr_sword");
		
		plr = new LocalPlayer(Sprites.get("player"), 30, 50);
		((LocalPlayer) plr).setSword(Sprites.get("plr_sword"));
		network.connect();
	}
	
	/*
	 * @see ba.idrol.net.GameComponent#update(int)
	 * Update loop for game component.
	 */
	@Override
	public void update(int delta){
		for(GameObject obj: this.objList){
			obj.update();
		}
	}
	
	/*
	 * @see ba.idrol.net.GameComponent#render()
	 * Render loop for game component
	 */
	@Override
	public void render(){
		for(GameObject obj: this.objList){
			obj.render();
		}
	}
	
	/*
	 * Creates graphics features for the world.
	 */
	public void world(){
		bg = new GameObject(800, 600, new Sprite("/res/images/world/bg.png"), 0, 0);
		Sprite cloud = new Sprite("/res/images/world/cloud.png");
		
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
	}
}
