package ba.idrol.net.StartMenu;


import ba.idrol.net.Sprite;

/*
 * Class for Login button.
 */
public class Login extends Button {

	public Login(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	protected void buttonClicked(){
		LogginNetwork login = new LogginNetwork();
		login.connect(Menu.playerName.getText(), Menu.password.getText());
	}
	
}