package ba.idrol.Menu;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

public class Exit extends Button {

	public Exit(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	protected void buttonClicked(){
		Main.destroy(Main.SHUTDOWN); // Cleans game
	}
	
}
