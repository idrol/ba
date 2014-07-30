package ba.idrol.server;


import org.lwjgl.input.Keyboard;

import ba.idrol.packets.PacketPlayerKeyPress;
import ba.idrol.packets.PacketPositionUpdatePlayer;

import com.esotericsoftware.kryonet.Connection;

public class Player extends GameObject {
	public Player(int width, int height, float x, float y) {
		super(width, height, x, y);
	}
	public Player(){
		super(32, 32, 100, 75);
	}
	public int id;
	private boolean moveLeft = false, moveRight = false, jump = false, direction = RIGHT;
	private static final boolean RIGHT = false, LEFT = true;
	
	public void keyPress(PacketPlayerKeyPress packet){
		if(packet.keyPressed == Keyboard.KEY_LEFT){
			if(packet.keyReleased){
				this.moveLeft = false;
			}else{
				this.moveLeft = true;
			}
		}else if(packet.keyPressed == Keyboard.KEY_RIGHT){
			if(packet.keyReleased){
				this.moveRight = false;
			}else{
				this.moveRight = true;
			}
		}else if(packet.keyPressed == Keyboard.KEY_UP){
			if(packet.keyReleased){
				this.jump = false;
			}else{
				this.jump = true;
			}
		}
	}
	
	public void update(){
		float xCopy = this.x;
		float movex = 0;
		if(this.moveLeft){
			xCopy -= 0.3f * BaServer.global_delta;
			movex = -(this.x - xCopy);
		}
		if(this.moveRight){
			xCopy += 0.3f * BaServer.global_delta;
			movex = xCopy - this.x;
		}
		if(this.jump){
			this.jump();
		}
		if(this.hasGravity){
			this.fall();
		}
		if(this.move(movex)){
			PacketPositionUpdatePlayer packet = new PacketPositionUpdatePlayer();
			packet.x = this.x;
			packet.y = this.y;
			packet.id = this.id;
			if(movex > 0){
				this.direction = LEFT;
			}else if(movex < 0){
				this.direction = RIGHT;
			}
			packet.direction = this.direction;
			BaServer.server.sendToAllTCP(packet);
		}
	}
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
	}
}
