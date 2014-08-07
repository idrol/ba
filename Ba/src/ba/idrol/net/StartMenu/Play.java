package ba.idrol.net.StartMenu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Game.Game;
import ba.idrol.net.Lobby.Lobby;
import ba.idrol.net.util.UrlUtils;

/*
 * Object for play button.
 */
public class Play extends Button {

	public Play(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	protected void buttonClicked(){
//		Main.currentGameComponent = new Game(Menu.playerName.getText());
		try {
			String isLoggedIn = UrlUtils.excutePost("http://www.idrol.net/ba/login", "user="+URLEncoder.encode(Menu.playerName.getText(), "UTF-8")+"&pass="+URLEncoder.encode(Menu.password.getText(), "UTF-8"));
			isLoggedIn = isLoggedIn.replace("\n", "").replace("\r", "");
			System.out.print(isLoggedIn);
			if(isLoggedIn.equals("true")){
				Main.currentGameComponent = new Lobby();
				Main.currentGameComponent.loadObjects();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}
