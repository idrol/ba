package ba.idrol.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;

public class LogginNetwork extends Listener{
	private Client client;
	private String ip = "idrol.net";
	private int port = 25556;
	// TODO: Need beter solution for this.
	private Map<String, Integer> unauthenticatedUsers = new HashMap<String, Integer>();
	private BlockingQueue queue = new ArrayBlockingQueue(4);
	
	public void start(){
			client = new Client();
			client.getKryo().register(PacketLogin.class);
			client.getKryo().register(PacketStatusRequest.class);
			client.addListener(this);
			client.start();
			try {
				client.connect(5000, ip, port);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void connect(PacketStatusRequest packet, int id){
		System.out.println("Connecting: "+packet.userName);
		client.sendTCP(packet);
		unauthenticatedUsers.put(packet.userName, id);
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketStatusRequest){
			PacketStatusRequest packet = (PacketStatusRequest)o;
			System.out.println("Received status request from loggin server with status! "+packet.allowed);
			if(packet.allowed){
				if(unauthenticatedUsers.containsKey(packet.userName)){
					User user = new User();
					user.username = packet.userName;
					user.id = unauthenticatedUsers.get(packet.userName);
					LobbyServerMain.loggedInUsers.put(packet.userName, user);
					unauthenticatedUsers.remove(packet.userName);
					System.out.println("Sending to: "+user.id);
					LobbyServerMain.server.sendToTCP(user.id, packet);
				}
			}else{
				if(unauthenticatedUsers.containsKey(packet.userName)){
					unauthenticatedUsers.remove(packet.userName);
				}
			}
		}
	}
}
