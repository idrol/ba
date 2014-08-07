package ba.idrol.net.client;

import java.io.IOException;

import ba.idrol.net.GameObject;
import ba.idrol.net.Sprites;
import ba.idrol.net.Game.Game;
import ba.idrol.net.Game.LocalPlayer;
import ba.idrol.net.Game.MpPlayer;
import ba.idrol.net.packets.PacketAddGameObject;
import ba.idrol.net.packets.PacketAddPlayer;
import ba.idrol.net.packets.PacketPlayerKeyPress;
import ba.idrol.net.packets.PacketPositionUpdatePlayer;
import ba.idrol.net.packets.PacketRegisterName;
import ba.idrol.net.packets.PacketRemovePlayer;
import ba.idrol.net.packets.PacketUpdatePlayerHealth;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/*
 * Class that handles all network communication on client side.
 */
public class Network extends Listener {
	
	// Stores the client object in a public static variable.
	public static Client client;
	// The ip to connect to.
	private String ip = "85.134.54.222";
	// The port to connect to.
	private int port = 25555;
	
	private String name;
	
	public Network(String name){
		super();
		this.name = name;
	}
	
	/*
	 * Methode that connects the client to the server.
	 */
	public void connect(){
		client = new Client();
		client.getKryo().register(PacketPositionUpdatePlayer.class);
		client.getKryo().register(PacketAddPlayer.class);
		client.getKryo().register(PacketRemovePlayer.class);
		client.getKryo().register(PacketPlayerKeyPress.class);
		client.getKryo().register(PacketAddGameObject.class);
		client.getKryo().register(PacketUpdatePlayerHealth.class);
		client.addListener(this);
		client.start();
		PacketRegisterName plrName = new PacketRegisterName();
		plrName.name = this.name;
		client.sendTCP(plrName);
		try {
			client.connect(5000, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @see com.esotericsoftware.kryonet.Listener#received(com.esotericsoftware.kryonet.Connection, java.lang.Object)
	 * 
	 * Handles messages received from the server.
	 */
	public void received(Connection c, Object o){
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
				Game.players.get(packet.id).attacking = true;
			}
		}else if(o instanceof PacketUpdatePlayerHealth){
			PacketUpdatePlayerHealth packet = (PacketUpdatePlayerHealth)o;
			if(packet.id == c.getID()){
				((LocalPlayer)Game.plr).health = packet.health;
			}else{
				Game.players.get(packet.id).health = packet.health;
			}
		}
	}
}
