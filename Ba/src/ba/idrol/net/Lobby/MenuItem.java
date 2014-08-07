package ba.idrol.net.Lobby;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;

import ba.idrol.net.GameObject;

public class MenuItem extends GameObject {
	private Color color;
	public MenuItem(float x, float y, int width, int height, Color color){
		super(width, height, x, y);
		this.color = color;
	}
	
	public void render(){
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
			glColor4f(this.color.r, this.color.g, this.color.b, this.color.a);
			glTranslatef(this.x, this.y, 0);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 1);
				glVertex2f(0, this.height);
				glTexCoord2f(1, 1);
				glVertex2f(this.width, this.height);
				glTexCoord2f(1, 0);
				glVertex2f(this.width, 0);
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
			glEnd();
		glPopMatrix();
		glColor4f(1,1,1,1);
		glEnable(GL_TEXTURE_2D);
	}
}
