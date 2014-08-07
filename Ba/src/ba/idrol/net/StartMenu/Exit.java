package ba.idrol.net.StartMenu;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

/*
 * Class for exit button.
 */
public class Exit extends Button {

	public Exit(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	protected void buttonClicked(){
		Main.destroy(Main.SHUTDOWN); // Cleans game
	}
	
}
