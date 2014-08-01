package ba.idrol.server;


import org.lwjgl.input.Keyboard;

import ba.idrol.packets.PacketPlayerKeyPress;
import ba.idrol.packets.PacketPositionUpdatePlayer;

/*
 * GameObject class for player that stores all player actions and methods.
 */
public class Player extends GameObject {
	
	// The players unique network id.
	public int id;
	// Boolean variables for storing movement/actions.
	private boolean moveLeft = false, moveRight = false, jump = false, direction = RIGHT, attacking = false;
	// Defines which values the 2 directions use to clarify things for the boolean direction.
	private static final boolean RIGHT = false, LEFT = true;
	// Declares which values the mouse buttons uses.
	private static final int LEFT_MOUSE_BUTTON = 0, MIDDLE_MOUSE_BUTTON = 2, RIGHT_MOUSE_BUTTON = 1;
	
	/*
	 * Create new player with specified size and position.
	 */
	public Player(int width, int height, float x, float y) {
		super(width, height, x, y);
	}
	/*
	 * Create new player with default size and position.
	 */
	public Player(){
		super(32, 32, 100, 75);
	}
	
	/*
	 * Processes player key presses and sets the booleans accordingly.
	 */
	public void keyPress(PacketPlayerKeyPress packet){
		if(!packet.isMouse){
			/*
			 * Keyboard events.
			 */
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
			/*
			 * Mouse events.
			 */
			if(packet.keyPressed == LEFT_MOUSE_BUTTON){
				BaServer.server.sendToAllExceptTCP(packet.id, packet);
				if(packet.keyReleased){
					this.attacking = false;
				}else{
					this.attacking = true;
				}
			}
		}
	}
	
	/*
	 * @see ba.idrol.server.GameObject#update()
	 * 
	 * Custom update method overridden from GameObject.
	 */
	public void update(){
		// Copy x to prevent changes to the real x.
		float xCopy = this.x;
		// The amount to move x by.
		float movex = 0;
		if(this.moveLeft){
			xCopy -= 0.3f * BaServer.getDeltaTime();
			movex = -(this.x - xCopy);
		}
		if(this.moveRight){
			xCopy += 0.3f * BaServer.getDeltaTime();
			movex = xCopy - this.x;
		}
		// If jump is true call the jump method.
		if(this.jump){
			this.jump();
		}
		// If gravity enabled make the player fall.
		// @TODO: Add so that player can fly around instead.
		if(this.hasGravity){
			this.fall();
		}
		// Move the player will return false if player collided.
		if(this.move(movex)){
			// Send update to players and set direction.
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
		// If player is attacking check if he hit something.
		if(this.attacking){
			checkForHit();
		}
	}
	
	/*
	 * Checks if another player is inside the damage box then hit that player.
	 */
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
	
	// @TODO: Implement this method and player health.
	private void hit() {
		
	}
	
	/*
	 * Sets the vertical speed to positive which causes the player to jump.
	 */
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
	}
}
