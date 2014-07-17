package ba.idrol.Menu;

import ba.idrol.Game.Game;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

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
