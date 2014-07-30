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
	private boolean moveLeft = false, moveRight = false, jump = false, direction = RIGHT, attacking = false;
	private static final boolean RIGHT = false, LEFT = true;
	
	public void keyPress(PacketPlayerKeyPress packet){
		if(!packet.isMouse){
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
		}else{
			if(packet.keyPressed == 0){
				BaServer.server.sendToAllExceptTCP(packet.id, packet);
				if(packet.keyReleased){
					this.attacking = false;
				}else{
					this.attacking = true;
				}
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
		if(this.attacking){
			checkForHit();
		}
	}
	
	public void checkForHit(){
		for(Player p: BaServer.players.values()){
			if(!p.equals(this)){
				if(this.direction == LEFT){
					if(p.x <= this.x+this.width+16 &&
					   p.x >= this.x+this.width &&
					   p.y <= this.y+this.height &&
					   p.y >= this.y){
						System.out.println("Player "+p.id+" has been hit by: "+this.id);
						p.hit();
					}
				}
			}
		}
	}
	
	private void hit() {
		
	}
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
	}
}
