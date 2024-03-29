package ba.idrol.net.StartMenu;


import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.User;
import ba.idrol.net.StartMenu.packets.PacketLogin;

/*
 * Class for Login button.
 */
public class Login extends Button {

	public Login(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	public void buttonClicked(){
		LogginNetwork login = new LogginNetwork();
		login.connect(Menu.playerName.getText(), Menu.password.getText());
		try {
			PacketLogin packet = login.getAnswer();
			if(packet.isLoggedIn){
				login.disconnect();
				User user = new User();
				user.userName = packet.userName;
				user.salt = packet.saltHash;
				System.out.println(user.toString());
				Main.setCurrentUser(user);
				Menu.switchToLobby();
			}else{
				Menu.displayFailedLoggin();
			}
			login.disconnect();
		} catch (InterruptedException e) {
			login.disconnect();
			e.printStackTrace();
		}
	}
	
}