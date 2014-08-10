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
	private BlockingQueue queue = new ArrayBlockingQueue(1);
	
	public boolean connect(String username, String sec_key){
		client = new Client();
		client.getKryo().register(PacketLogin.class);
		client.getKryo().register(PacketStatusRequest.class);
		client.addListener(this);
		client.start();
		try {
			client.connect(5000, ip, port);
			PacketStatusRequest packet = new PacketStatusRequest();
			packet.userName = username;
			packet.securityHash = sec_key;
			client.sendTCP(packet);
			return (boolean) queue.take();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketStatusRequest){
			PacketStatusRequest packet = (PacketStatusRequest)o;
			try {
				queue.put(packet.allowed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(packet.allowed);
		}
	}
}

