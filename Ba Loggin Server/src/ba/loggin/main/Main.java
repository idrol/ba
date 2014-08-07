package ba.loggin.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;








import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Main extends Listener {
		private Map<String, User> loggedInUsers = new HashMap<String, User>();
		private Server server;
		private int port = 25556;
		public void start(){
			server = new Server();
			server.getKryo().register(PacketLogin.class);
			try {
				server.bind(port);
				server.start();
				server.addListener(this);
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
			if(o instanceof PacketLogin){
				PacketLogin packet = (PacketLogin)o;
				String isLoggedIn;
				try {
					isLoggedIn = UrlUtils.excutePost("http://www.idrol.net/ba/login", "user="+URLEncoder.encode(packet.userName, "UTF-8")+"&pass="+URLEncoder.encode(packet.passWord, "UTF-8"));
					isLoggedIn = isLoggedIn.replace("\n", "").replace("\r", "");
					PacketLogin packet2 = new PacketLogin();
					if(isLoggedIn.equals("true")){
						System.out.println("User: "+packet.userName+" logged in!");
						packet2.isLoggedIn = true;
						User user = new User();
						user.username = packet.userName;
						user.hash = MD5.generateMD5(Long.toString(System.nanoTime()/1000000));
						loggedInUsers.put(packet.userName, user);
					}else{
						packet2.isLoggedIn = false;
					}
					server.sendToTCP(c.getID(), packet2);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
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
			new Main().start();
		}

}
