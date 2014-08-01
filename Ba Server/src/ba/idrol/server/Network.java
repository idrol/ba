package ba.idrol.server;

import ba.idrol.packets.PacketAddGameObject;
import ba.idrol.packets.PacketAddPlayer;
import ba.idrol.packets.PacketPlayerKeyPress;
import ba.idrol.packets.PacketRemovePlayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/*
 * Main network class that handles all communication to clients.
 */
public class Network extends Listener {
	
	/*
	 * @see com.esotericsoftware.kryonet.Listener#connected(com.esotericsoftware.kryonet.Connection)
	 * 
	 * Connected method overridden from Listener
	 * Handles client connections and notifies other users that a player has joined.
	 */
	public void connected(Connection c){
		PacketAddPlayer packet = new PacketAddPlayer();
		packet.id = c.getID();
		BaServer.server.sendToAllExceptTCP(c.getID(), packet);
		
		for(Player p: BaServer.players.values()){
			PacketAddPlayer packet2 = new PacketAddPlayer();
			packet2.id = p.id;
			c.sendTCP(packet2);
		}
		for(GameObject o: GameObject.networkedObjects){
			PacketAddGameObject packet2 = new PacketAddGameObject();
			packet2.id = c.getID();
			packet2.width = o.width;
			packet2.height = o.height;
			packet2.x = o.x;
			packet2.y = o.y;
			packet2.spriteName = o.spriteName;
			c.sendTCP(packet2);
		}
		Player player = (Player) new Player().disableCollision().enableGravity();
		player.id = packet.id;
		BaServer.players.put(packet.id, player);
		System.out.println("Player Connection recived!");
	}
	
	/*
	 * @see com.esotericsoftware.kryonet.Listener#received(com.esotericsoftware.kryonet.Connection, java.lang.Object)
	 * 
	 * Method that handles incoming packets from clients.
	 */
	public void received(Connection c, Object o){
		if(o instanceof PacketPlayerKeyPress){
			PacketPlayerKeyPress packet1 = (PacketPlayerKeyPress)o;
			packet1.id = c.getID();
			BaServer.players.get(packet1.id).keyPress(packet1);
		}
	}
	
	/*
	 * @see com.esotericsoftware.kryonet.Listener#disconnected(com.esotericsoftware.kryonet.Connection)
	 * 
	 * Method that handles client disconnects and notifies other clients.
	 */
	public void disconnected(Connection c){
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		BaServer.players.remove(packet.id);
		BaServer.server.sendToAllExceptTCP(c.getID(), packet);
		System.out.println("Player disconnected!");
	}
}
