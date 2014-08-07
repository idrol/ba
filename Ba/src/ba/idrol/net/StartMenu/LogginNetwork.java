package ba.idrol.net.StartMenu;

import java.io.IOException;

import ba.idrol.net.StartMenu.packets.PacketLogin;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LogginNetwork extends Listener {
	
	private Client client;
	private String ip = "idrol.net";
	private int port = 25556;
	
	public void connect(String userName, String password){
		client = new Client();
		client.getKryo().register(PacketLogin.class);
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
	
	public void received(Connection c, Object o){
		if(o instanceof PacketLogin){
			PacketLogin packet = (PacketLogin)o;
			System.out.println("Login status is: "+packet.isLoggedIn);
		}
	}
}
