package ba.idrol.net;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;


public class GameObject {
	protected int width, height;
	protected float x, y;
	protected Sprite texture;
	
	public static List<GameObject> objList = new ArrayList<GameObject>();
	
	private GameObject(int width, int height, float x, float y){
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		GameObject.objList.add(this);
	}
	public GameObject(int width, int height, Sprite sprite, float x, float y){
		this(width, height, x, y);
		this.texture = sprite;
	}
	public GameObject(Sprite sprite){
		this(sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite, 0, 0);
	}
	public GameObject(Sprite sprite, float x, float y){
		this(sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite, x, y);
	}
	protected float getTop(){
		return this.y+this.height;
	}
	protected float getBot(){
		return this.y;
	}
	protected float getLeft(){
		return this.x;
	}
	protected float getRight(){
		return this.x+this.width;
	}
	//Movment is always on positive axis
	public boolean move(float x, float y){
		float oldx = this.x;
		float oldy = this.y;
		this.x += x;
		this.y += y;
		for(GameObject obj: GameObject.objList){
			if(!obj.equals(this)){
				if(this.x < obj.x + obj.width &&
						   this.x + this.width > obj.x &&
						   this.y < obj.y + obj.height &&
						   this.height + this.y > obj.y){
					this.x = oldx;
					this.y = oldy;
					return true;
				}
			}
		}
		return false;
		
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
	
	public void update(){
		
	}
}
