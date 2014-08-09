package ba.idrol.net.Lobby;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ba.idrol.net.StartMenu.packets.PacketLogin;
import ba.idrol.net.StartMenu.packets.PacketStatusRequest;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyNetwork extends Listener {
	
	private Client client;
	private String ip = "idrol.net";
	private int port = 25557;
	
	public boolean connect(String username, String sec_key){
		client = new Client();
		client.addListener(this);
		client.start();
		try {
			client.connect(5000, ip, port);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void received(Connection c, Object o){
	}
}

