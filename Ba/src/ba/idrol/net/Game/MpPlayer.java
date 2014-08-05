package ba.idrol.net.Game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.HashMap;
import java.util.Map;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Sprites;
import ba.idrol.net.client.MpPlayerData;

public class MpPlayer extends Player {
	// Connection id for mp player.
	public int id;
	// Status if player is attacking.
	public boolean attacking = false;
	// Current sprite for sword.
	private Sprite sword;
	// Current sword rotation.
	private float swordRot = 180;
	
	/*
	 * Creates new player at specified location.
	 */
	public MpPlayer(float x, float y){
		super(Sprites.get("player"), x, y);
	}
	
	/*
	 * Sets players x position.
	 */
	public void setX(float x){
		this.x = x;
	}
	
	/*
	 * Sets players y position.
	 */
	public void setY(float y){
		this.y = y;
	}
	
	/*
	 * Sets the current sword sprite.
	 */
	public void setSword(Sprite sprite){
		this.sword = sprite;
	}
	
	/*
	 * @see ba.idrol.net.GameObject#update()
	 * 
	 * Player update method only updates sword rot.
	 */
	@Override
	public void update(){
		if(this.attacking){
			this.swordRot += 0.5f*Main.getDeltaTime();
			if(this.swordRot > 340 || this.swordRot < -140){
				this.swordRot = 180;
			}
		}
	}
	
	/*
	 * @see ba.idrol.net.GameObject#render()
	 * 
	 * Render method for player
	 */
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
	

}
