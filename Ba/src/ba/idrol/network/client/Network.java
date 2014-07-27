package ba.idrol.network.client;

import java.io.IOException;

import ba.idrol.Game.Game;
import ba.idrol.server.packets.PacketAddPlayer;
import ba.idrol.server.packets.PacketPositionUpdate;
import ba.idrol.server.packets.PacketRemovePlayer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Network extends Listener {
	public Client client;
	private String ip = "85.134.54.222";
	private int port = 25555;
	
	public void connect(){
		client = new Client();
		client.getKryo().register(PacketPositionUpdate.class);
		client.getKryo().register(PacketAddPlayer.class);
		client.getKryo().register(PacketRemovePlayer.class);
		client.addListener(this);
		client.start();
		try {
			client.connect(5000, ip, port, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketAddPlayer){
			PacketAddPlayer packet = (PacketAddPlayer)o;
			MpPlayer newPlayer = new MpPlayer();
			Game.players.put(packet.id, newPlayer);
		}else if(o instanceof PacketRemovePlayer){
			PacketRemovePlayer packet= (PacketRemovePlayer)o;
			Game.players.remove(packet.id);
		}else if(o instanceof PacketPositionUpdate){
			PacketPositionUpdate packet = (PacketPositionUpdate)o;
			Game.players.get(packet.id).x = packet.x;
			Game.players.get(packet.id).y = packet.y;
		}
	}
}
