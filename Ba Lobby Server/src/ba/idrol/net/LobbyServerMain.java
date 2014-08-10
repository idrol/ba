package ba.idrol.net;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class LobbyServerMain extends Listener {
	public static Map<String, User> loggedInUsers = new ConcurrentHashMap<String, User>();
	public static Server server;
	private int port = 25557;
	private LogginNetwork loggin;
	public void start(){
		server = new Server();
		server.getKryo().register(PacketLogin.class);
		server.getKryo().register(PacketStatusRequest.class);
		try {
			server.bind(port);
			server.start();
			server.addListener(this);
			loggin = new LogginNetwork();
			loggin.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connected(Connection c){
		
	}
	
	/*
	 * @see com.esotericsoftware.kryonet.Listener#received(com.esotericsoftware.kryonet.Connection, java.lang.Object)
	 * 
	 * Method that handles incoming packets from clients.
	 */
	public void received(Connection c, Object o){
		if(o instanceof PacketStatusRequest){
			PacketStatusRequest packet = (PacketStatusRequest)o;
			loggin.connect(packet, c.getID());
		}
	}
	
	/*
	 * @see com.esotericsoftware.kryonet.Listener#disconnected(com.esotericsoftware.kryonet.Connection)
	 * 
	 * Method that handles client disconnects and notifies other clients.
	 */
	public void disconnected(Connection c){
		
	}
	
	public static void main(String[] args){
		new LobbyServerMain().start();
	}
}
