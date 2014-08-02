package ba.idrol.net.Game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.client.Network;
import ba.idrol.net.packets.PacketPlayerKeyPress;
import ba.idrol.net.packets.PacketPositionUpdatePlayer;
import static org.lwjgl.opengl.GL11.*;

/*
 * Player object only used for local player.
 */
public class LocalPlayer extends Player {
	// Current sword rotation.
	private float swordRot = 0;
	// Boolean variables for storing movement/actions.
	private boolean jumping = false, moving_left = false, moving_right = false, attacking = false;
	// Stores the sprite for the sword.
	private Sprite sword;
	
	/*
	 * Creates new local player with sprite and pos.
	 */
	public LocalPlayer(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	/*
	 * @see ba.idrol.net.GameObject#update()
	 * Update method for the local player
	 * Every key event is recorded here and send to server.
	 */
	@Override
	public void update(){
		super.update();
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_LEFT;
			Network.client.sendTCP(packet);
			this.moving_left = true;
		}else if(this.moving_left){
			this.moving_left = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_LEFT;
			packet.keyReleased = true;
			Network.client.sendTCP(packet);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_RIGHT;
			Network.client.sendTCP(packet);
			this.moving_right = true;
		}else if(this.moving_right){
			this.moving_right = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_RIGHT;
			packet.keyReleased = true;
			Network.client.sendTCP(packet);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_UP;
			Network.client.sendTCP(packet);
			this.jumping = true;
		}else if(this.jumping){
			this.jumping = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_UP;
			packet.keyReleased = true;
			Network.client.sendTCP(packet);
		}
		// Left mouse button = 0, right = 1, Midle = 2;
		if (Mouse.isButtonDown(0)){
			this.swordRot += 0.5f*Main.getDeltaTime();
			if(this.swordRot > 140f){
				this.swordRot = 0;
			}
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = 0;
			packet.isMouse = true;
			Network.client.sendTCP(packet);
			this.attacking = true;
		}else if(this.attacking){
			this.attacking = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = 0;
			packet.keyReleased = true;
			packet.isMouse = true;
			Network.client.sendTCP(packet);
		}
	}
	
	/*
	 * @see ba.idrol.net.GameObject#render()
	 * Renders the player and sword if attacking.
	 */
	@Override
	public void render(){
		super.render();
		System.out.println(this.x+", "+this.y);
		glPushMatrix();
			glTranslatef(this.x, this.y+32, 0);
			glColor3f(1, 0, 0);
			glBegin(GL_QUADS);
				glVertex2f(0, 0);
				glVertex2f(32, 0);
				glVertex2f(32, 32);
				glVertex2f(0, 32);
			glEnd();
		glPopMatrix();
		glColor3f(1, 1, 1);
		if(this.attacking){
			this.sword.bind();
			glPushMatrix();
				glTranslatef(this.x, this.y+16, 0);
				glTranslatef(16, 0, 0);
				if(this.direction == LEFT){
					glRotatef(-this.swordRot, 0, 0, 1);
				}else{
					glRotatef(this.swordRot, 0, 0, 1);
				}
				glTranslatef(-16, 0, 0);
				glBegin(GL_QUADS);
					glTexCoord2f(0, 1);
					glVertex2f(0, 0);
					glTexCoord2f(1, 1);
					glVertex2f(this.sword.getTexture().getTextureWidth(), 0);
					glTexCoord2f(1, 0);
					glVertex2f(this.sword.getTexture().getTextureWidth(), this.sword.getTexture().getTextureHeight());
					glTexCoord2f(0, 0);
					glVertex2f(0, this.sword.getTexture().getTextureHeight());
				glEnd();
			glPopMatrix();
			glBindTexture(GL_TEXTURE_2D, 0);
		}
		
	}
	
	/*
	 * Sets the player x position.
	 */
	public void setX(float x){
		this.x = x;
	}
	
	/*
	 * Sets the player y position.
	 */
	public void setY(float y){
		this.y = y;
	}
	
	/*
	 * Sets the sword sprite.
	 */
	public void setSword(Sprite sprite){
		this.sword = sprite;
	}

}
