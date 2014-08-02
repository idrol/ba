package ba.idrol.net.Menu;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Game.Game;

/*
 * Object for play button.
 */
public class Play extends Button {

	public Play(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	protected void buttonClicked(){
		Main.currentGameComponent = new Game();
		Main.currentGameComponent.loadObjects();
	}
	
}
