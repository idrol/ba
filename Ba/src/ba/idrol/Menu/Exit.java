package ba.idrol.Menu;

import org.lwjgl.opengl.Display;

import ba.idrol.Game.Game;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

public class Exit extends Button {

	public Exit(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	protected void buttonClicked(){
		Display.destroy();
	}
	
}
