package ba.idrol.net;

import static org.lwjgl.opengl.GL11.*;


public class GameObject {
	protected int width, height;
	public float x, y;
	protected Sprite texture;
	
	protected static float GRAVITY = 0.04f;
	protected static float TERMINAL_VELOCITY = 2f;
	protected float vertical_speed = 0;
	
	protected boolean onGround = false;
	
	public boolean direction = LEFT;
	public static final boolean RIGHT = false, LEFT = true;
	
	public GameObject(int width, int height, float x, float y){
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		Main.currentGameComponent.addGameObject(this);;
		
	}
	public GameObject(int width, int height, Sprite sprite, float x, float y){
		this(width, height, x, y);
		this.texture = sprite;
	}
	public GameObject(Sprite sprite){
		this(sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite, 0, 0);
	}
	public GameObject(Sprite sprite, float x, float y){
		this((int)sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite, x, y);
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
	public void render(){
		this.texture.getTexture().bind();;
		glPushMatrix();
		if(this.direction == LEFT){
			glTranslatef(this.x, this.y, 0);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 1);
				glVertex2f(0, 0);
				glTexCoord2f(1, 1);
				glVertex2f(this.width, 0);
				glTexCoord2f(1, 0);
				glVertex2f(this.width, this.height);
				glTexCoord2f(0, 0);
				glVertex2f(0, this.height);
			glEnd();
		}else{
			glTranslatef(this.x, this.y, 0);
			glBegin(GL_QUADS);
				glTexCoord2f(1, 1);
				glVertex2f(0, 0);
				glTexCoord2f(0, 1);
				glVertex2f(this.width, 0);
				glTexCoord2f(0, 0);
				glVertex2f(this.width, this.height);
				glTexCoord2f(1, 0);
				glVertex2f(0, this.height);
			glEnd();
		}
		glPopMatrix();
		glColor3f(1,1,1);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glBegin(GL_QUADS);
			glVertex2f(this.x, this.y);
			glVertex2f(this.x+this.width, this.y);
			glVertex2f(this.x+this.width, this.y+this.height);
			glVertex2f(this.x, this.y+this.height);
		glEnd();
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		
	}
	public void update(){
		
	}
	public void destroy(){
		Main.currentGameComponent.objList.remove(this);
	}
	public void setTexture(Sprite sprite) {
		this.texture = sprite;
	}
}
