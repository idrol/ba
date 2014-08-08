package ba.idrol.net.StartMenu;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ba.idrol.net.StartMenu.packets.PacketLogin;
import ba.idrol.net.StartMenu.packets.PacketStatusRequest;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LogginNetwork extends Listener {
	
	private Client client;
	private String ip = "idrol.net";
	private int port = 25556;
	private BlockingQueue answer = new ArrayBlockingQueue(4);
	public void connect(String userName, String password){
		client = new Client();
		client.getKryo().register(PacketLogin.class);
		client.getKryo().register(PacketStatusRequest.class);
		client.addListener(this);
		client.start();
		try {
			client.connect(5000, ip, port);
			PacketLogin packet = new PacketLogin();
			packet.userName = userName;
			packet.passWord = password;
			client.sendTCP(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public PacketLogin getAnswer() throws InterruptedException{
		return (PacketLogin) answer.take();
	}
	
	public void disconnect(){
		client.stop();
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketLogin){
			PacketLogin packet = (PacketLogin)o;
			try {
				answer.put(packet);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
