package ba.idrol.Game;

import org.lwjgl.input.Keyboard;

import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

public class Player extends GameObject{
	
	
	
	public Player(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}

	@Override
	public void update(){
		super.update();
		int x = 0, y = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * Main.getDeltaTime();
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * Main.getDeltaTime();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) this.jump();
		this.move(x, y);
	}
	
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
	}

}
