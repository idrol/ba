package ba.idrol.server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import ba.idrol.packets.PacketAddGameObject;
import ba.idrol.packets.PacketAddPlayer;
import ba.idrol.packets.PacketPlayerKeyPress;
import ba.idrol.packets.PacketPositionUpdatePlayer;
import ba.idrol.packets.PacketRemovePlayer;

import com.esotericsoftware.kryonet.Server;

public class BaServer{
	// Variable the stores the server object which holds all network communication.
	public static Server server;
	// Port to be used for network communication
	private static final int port = 25555;
	// Stores the time when the server was last updated and deltaTime stores the time differences between updates.
	private static long deltaTime = 0, lastUpdate = 0;
	// Boolean variable to keep track if game is running.
	public static boolean isRunning = true;
	// Stores all registered players on the server.
	public static ConcurrentMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	// Stores the world server side.
	private static World world;

	
	/*
	 * Starts the server loop that runs all updates. 
	 */
	public void start(){
		lastUpdate = getTime();
		while(isRunning){
			update();
			deltaTime = getDelta();
//			System.out.println("Global delta is: " + global_delta);
			Display.sync(60);
		}
	}
	
	/*
	 * Updates the server.
	 */
	private void update() {
		for(Player p:players.values()){
			p.update();
		}
	}
	
	/*
	 * Method that other classes uses to access the delta time variable
	 */
	public static int getDeltaTime(){
		return (int) deltaTime;
	}
	
	/*
	 * Gets the delta time since this method was last run so only run this once at the end of the game loop or you break all updates.
	 */
	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastUpdate);
		lastUpdate = time;
		return delta;
		
	}
	
	/*
	 * Gets the current time in milli seconds.
	 * To get it in seconds just divide by 1000.
	 */
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
