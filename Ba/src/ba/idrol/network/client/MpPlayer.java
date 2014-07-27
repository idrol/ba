package ba.idrol.network.client;

import java.util.HashMap;
import java.util.Map;

import ba.idrol.Game.Player;
import ba.idrol.net.Sprite;

public class MpPlayer extends Player {
	private int id;
	public MpPlayer(){
		super(new Sprite("/res/images/characeter/char.png"), 100, 100);
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}

}
