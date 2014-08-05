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
	private float swordRot = 180;
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
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)){
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
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
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
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
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
		if (Mouse.isButtonDown(0) || Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			this.swordRot += 0.5f*Main.getDeltaTime();
			if(this.swordRot > 340f){
				this.swordRot = 180;
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
		if(this.attacking){
			this.sword.bind();
			glPushMatrix();
				glTranslatef(this.x, this.y+16, 0);
				glTranslatef(16, 0, 0);
				if(this.direction == LEFT){
					glRotatef(this.swordRot, 0, 0, 1);
				}else{
					glRotatef(-this.swordRot, 0, 0, 1);
				}
				glTranslatef(-16, 0, 0);
				glBegin(GL_QUADS);
					glTexCoord2f(0, 1);
					glVertex2f(0, this.sword.getTexture().getTextureHeight());
					glTexCoord2f(1, 1);
					glVertex2f(this.sword.getTexture().getTextureWidth(), this.sword.getTexture().getTextureHeight());
					glTexCoord2f(1, 0);
					glVertex2f(this.sword.getTexture().getTextureWidth(), 0);
					glTexCoord2f(0, 0);
					glVertex2f(0, 0);
				glEnd();
			glPopMatrix();
		}
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
			glTranslatef(this.x, this.y-14, 0);
			glColor3f(1, 0, 0);
			glBegin(GL_QUADS);
				glVertex2f(0, 0);
				glVertex2f(32, 0);
				glVertex2f(32, 6);
				glVertex2f(0, 6);
			glEnd();
			glColor3f(0, 1, 0);
			System.out.println(this.health);
			glBegin(GL_QUADS);
				glVertex2f(0, 0);
				glVertex2f(32*(this.health/100), 0);
				glVertex2f(32*(this.health/100), 6);
				glVertex2f(0, 6);
			glEnd();
		glPopMatrix();
		glColor3f(1, 1, 1);
		glEnable(GL_TEXTURE_2D);
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
