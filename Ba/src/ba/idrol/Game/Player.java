package ba.idrol.Game;

import org.lwjgl.input.Keyboard;

import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;

public class Player extends GameObject{
	
	private static float GRAVITY = 0.5f;
	private static float TERMINAL_VELOCITY = 10f;
	private float vertical_speed = 0;
	
	public Player(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}

	@Override
	public void update(){
		int x = 0, y = 0;
		this.fall();
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * Main.getDeltaTime();
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * Main.getDeltaTime();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y += 0.50f * Main.getDeltaTime();
		this.move(x, y);
	}
	public void fall(){
		this.vertical_speed -= GRAVITY;
		if(this.vertical_speed > TERMINAL_VELOCITY){
			this.vertical_speed = TERMINAL_VELOCITY;
		}
		if(this.move(0, this.vertical_speed)){
			this.vertical_speed = 0;
		}
	}
	public float jump(){
		
		return 0;
	}

}
