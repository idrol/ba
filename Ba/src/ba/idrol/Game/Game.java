package ba.idrol.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.esotericsoftware.minlog.Log;

import ba.idrol.Menu.Menu;
import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Sprites;
import ba.idrol.network.client.MpPlayerData;
import ba.idrol.network.client.Network;
import ba.idrol.server.packets.PacketAddGameObject;
import ba.idrol.server.packets.PacketAddPlayer;
import ba.idrol.server.packets.PacketPlayerKeyPress;
import ba.idrol.server.packets.PacketPositionUpdatePlayer;
import ba.idrol.server.packets.PacketRemovePlayer;

public class Game extends GameComponent {
	public static Map<Integer, MpPlayer> players = new HashMap<Integer, MpPlayer>();
	GameObject bg;
	public static GameObject plr;
	GameObject cloud_1, cloud_2, cloud_3, cloud_4, cloud_5, cloud_6, cloud_7, cloud_8, cloud_9, cloud_10;
	private static Network network;
	public static BlockingQueue queue = new ArrayBlockingQueue(1024);
	public Game(){
		network = new Network();
	}
	public static Network getNetwork(){
		return network;
	}
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
	@Override
	public void update(int delta){
		processPackets();
		for(GameObject obj: this.objList){
			obj.update();
		}
	}
	public void processPackets(){
		Object o;
		try {
			o = queue.take();
			if(o instanceof PacketAddPlayer){
				PacketAddPlayer packet = (PacketAddPlayer)o;
				MpPlayer newPlayer2 = (MpPlayer) new MpPlayer(100, 100);
				newPlayer2.id = packet.id;
				newPlayer2.setSword(Sprites.get("plr_sword"));
				System.out.println("Adding mpplayer with id: "+newPlayer2.id);
				Game.players.put(newPlayer2.id, newPlayer2);
			}else if(o instanceof PacketRemovePlayer){
				PacketRemovePlayer packet= (PacketRemovePlayer)o;
				Game.players.get(packet.id).destroy();
				Game.players.remove(packet.id);
			}else if(o instanceof PacketPositionUpdatePlayer){
				PacketPositionUpdatePlayer packet = (PacketPositionUpdatePlayer)o;
				if(packet.id == Network.client.getID()){
					((LocalPlayer) Game.plr).setX(packet.x);
					((LocalPlayer) Game.plr).setY(packet.y);
					((LocalPlayer) Game.plr).direction = packet.direction;
				}else{
					Game.players.get(packet.id).x = packet.x;
					Game.players.get(packet.id).y = packet.y;
					Game.players.get(packet.id).direction = packet.direction;
				}
			}else if(o instanceof PacketAddGameObject){
				PacketAddGameObject packet = (PacketAddGameObject)o;
				GameObject object = new GameObject(packet.width, packet.height, packet.x, packet.y);
				object.setTexture(Sprites.get(packet.spriteName));
			}else if(o instanceof PacketPlayerKeyPress){
				PacketPlayerKeyPress packet = (PacketPlayerKeyPress)o;
				if(packet.keyReleased){
					Game.players.get(packet.id).attacking = false;
				}else{
					System.out.println("Setting attacking to true");
					Game.players.get(packet.id).attacking = true;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void render(){
		for(GameObject obj: this.objList){
			obj.render();
		}
	}
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
