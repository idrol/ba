package ba.idrol.net;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;


public class Object {
	private int width, height, x, y;
	private Sprite texture;
	private Object(int width, int height){
		this.height = height;
		this.width = width;
		this.x = 0;
		this.y = 0;
	}
	public Object(int width, int height, Sprite sprite){
		this(width, height);
		this.texture = sprite;
	}
	public Object(Sprite sprite){
		this(sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite);
	}
	public void render(){
		this.texture.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 1);
			glVertex2f(this.x, this.y);
			glTexCoord2f(1, 1);
			glVertex2f(this.x+this.width, this.y);
			glTexCoord2f(1, 0);
			glVertex2f(this.x+this.width, this.y+this.height);
			glTexCoord2f(0, 0);
			glVertex2f(this.x, this.y+this.height);
		glEnd();
	}
}
