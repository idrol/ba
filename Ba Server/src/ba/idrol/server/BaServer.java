package ba.idrol.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import ba.idrol.packets.Packet;
import ba.idrol.packets.PacketAddGameObject;
import ba.idrol.packets.PacketAddPlayer;
import ba.idrol.packets.PacketPlayerKeyPress;
import ba.idrol.packets.PacketPositionUpdatePlayer;
import ba.idrol.packets.PacketRemovePlayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class BaServer{
	public static Server server;
	private static final int port = 25555;
	public static long global_delta = 0;
	private static long lastUpdate = 0;
	public static boolean isRunning = true;
	public static ConcurrentMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	private static World world;

	public BaServer(){
		
	}
	
	public void start(){
		lastUpdate = getTime();
		while(isRunning){
			update();
			global_delta = getDelta();
//			System.out.println("Global delta is: " + global_delta);
			Display.sync(60);
		}
	}
	
	private void update() {
		for(Player p:players.values()){
			p.update();
		}
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastUpdate);
		lastUpdate = time;
		return delta;
		
	}
	public static long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/*
	 * Java main method
	 * Creates the @World and server and adds @Network as listener to @Server
	 */
	public static void main(String[] args) throws IOException{
		world = new World();
		server = new Server();
		server.getKryo().register(PacketPositionUpdatePlayer.class);
		server.getKryo().register(PacketAddPlayer.class);
		server.getKryo().register(PacketRemovePlayer.class);
		server.getKryo().register(PacketPlayerKeyPress.class);
		server.getKryo().register(PacketAddGameObject.class);
		server.bind(port);
		server.start();
		server.addListener(new Network());
		new BaServer().start();
	}
}
