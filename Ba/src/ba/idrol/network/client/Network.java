package ba.idrol.network.client;

import java.io.IOException;

import ba.idrol.Game.Game;
import ba.idrol.Game.LocalPlayer;
import ba.idrol.Game.MpPlayer;
import ba.idrol.net.GameObject;
import ba.idrol.net.Sprites;
import ba.idrol.server.packets.PacketAddGameObject;
import ba.idrol.server.packets.PacketAddPlayer;
import ba.idrol.server.packets.PacketPlayerKeyPress;
import ba.idrol.server.packets.PacketPositionUpdatePlayer;
import ba.idrol.server.packets.PacketRemovePlayer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Network extends Listener {
	public static Client client;
	private String ip = "85.134.54.222";
	private int port = 25555;
	
	public void connect(){
		client = new Client();
		client.getKryo().register(PacketPositionUpdatePlayer.class);
		client.getKryo().register(PacketAddPlayer.class);
		client.getKryo().register(PacketRemovePlayer.class);
		client.getKryo().register(PacketPlayerKeyPress.class);
		client.getKryo().register(PacketAddGameObject.class);
		client.addListener(this);
		client.start();
		try {
			client.connect(5000, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
				System.out.println("Setting attacking to true");
				Game.players.get(packet.id).attacking = true;
			}
		}
	}
}
