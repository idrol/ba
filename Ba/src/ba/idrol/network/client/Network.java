package ba.idrol.network.client;

import java.io.IOException;

import ba.idrol.Game.Game;
import ba.idrol.Game.LocalPlayer;
import ba.idrol.Game.MpPlayer;
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
		try {
			Game.queue.put(o);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
