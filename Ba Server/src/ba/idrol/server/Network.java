package ba.idrol.server;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.Sys;

import ba.idrol.packets.PacketAddGameObject;
import ba.idrol.packets.PacketAddPlayer;
import ba.idrol.packets.PacketPlayerKeyPress;
import ba.idrol.packets.PacketPositionUpdatePlayer;
import ba.idrol.packets.PacketRemovePlayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Network extends Listener {
	
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
		try {
			BaServer.queue.put(packet);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Player Connection recived!");
	}
	
	public void received(Connection c, Object object){
		if(object instanceof PacketPlayerKeyPress){
			((PacketPlayerKeyPress) object).id = c.getID();
		}
		try {
			BaServer.queue.put(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void disconnected(Connection c){
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		try {
			BaServer.queue.put(packet);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BaServer.server.sendToAllExceptTCP(c.getID(), packet);
		System.out.println("Player disconnected!");
	}
}
