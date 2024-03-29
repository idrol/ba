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
import com.esotericsoftware.minlog.Log;

public class LogginServerMain extends Listener {
		private Map<String, User> loggedInUsers = new HashMap<String, User>();
		private Server server;
		private int port = 25556;
		private final String serverTag = "[Server]: ", recvTag = "[Received]: ", sentTag = "[Sent]: ";
		public void start(){
			server = new Server();
			server.getKryo().register(PacketLogin.class);
			server.getKryo().register(PacketStatusRequest.class);
			try {
				server.bind(port);
				server.start();
				server.addListener(this);
				Log.info("Loggin Server", "Server has started!");
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
						Log.info("Loggin Server", "User: "+packet.userName+" logged in!");
						packet2.isLoggedIn = true;
						packet2.userName = packet.userName;
						User user = new User();
						user.username = packet.userName;
						user.hash = MD5.generateMD5(Long.toString(System.nanoTime()/1000000));
						packet2.saltHash = user.hash;
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
			if(o instanceof PacketStatusRequest){
				PacketStatusRequest packet = (PacketStatusRequest)o;
				PacketStatusRequest packet2 = new PacketStatusRequest();
				packet2.userName = packet.userName;
				String sec_key = "";
				if(loggedInUsers.containsKey(packet.userName)){
					User user = loggedInUsers.get(packet.userName);
					sec_key = packet.userName+"+"+user.hash;
					try {
						sec_key = MD5.generateMD5(sec_key);
						if(sec_key.equals(packet.securityHash)){
							packet2.allowed = true;
						}
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if(packet2.allowed){
					Log.info("Loggin Server", "Allowing: "+packet.userName); 
				}else{
					Log.info("Loggin Server", "Denying: "+packet.userName); 
				}
				server.sendToTCP(c.getID(), packet2);
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
			new LogginServerMain().start();
		}

}
