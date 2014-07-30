package ba.idrol.Game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.network.client.Network;
import ba.idrol.server.packets.PacketPlayerKeyPress;
import ba.idrol.server.packets.PacketPositionUpdatePlayer;
import static org.lwjgl.opengl.GL11.*;

public class LocalPlayer extends Player {
	private float networkX = 0, networkY = 0, swordRot = 0;
	private boolean jumping = false, moving_left = false, moving_right = false, attacking = false;
	private Sprite sword;
	public LocalPlayer(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
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
	
	@Override
	public void render(){
		super.render();
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
		}
	}
	
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public void setSword(Sprite sprite){
		this.sword = sprite;
	}

}
