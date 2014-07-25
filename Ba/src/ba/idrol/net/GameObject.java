package ba.idrol.net;

import static org.lwjgl.opengl.GL11.*;


public class GameObject {
	protected int width, height;
	protected float x, y;
	protected Sprite texture;
	
	protected static float GRAVITY = 0.04f;
	protected static float TERMINAL_VELOCITY = 2f;
	protected float vertical_speed = 0;
	
	protected boolean onGround = false;
	protected boolean hasGravity = false;
	
	protected boolean hasColisionBox = true;
	
	private GameObject(int width, int height, float x, float y){
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
	public GameObject enableGravity(){
		this.hasGravity = true;
		return this;
	}
	public GameObject disableCollision(){
		this.hasColisionBox = false;
		return this;
	}
	public boolean getCollStat(){
		return this.hasColisionBox;
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
		for(GameObject obj: Main.currentGameComponent.getGameObjectList()){
			if(!obj.equals(this)){
				if(obj.hasColisionBox){
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
		}
		return false;
		
	}
	public void render(){
		this.texture.getTexture().bind();;
		glPushMatrix();
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
	
	public void fall(){
		this.vertical_speed -= GRAVITY;
		if(this.vertical_speed > TERMINAL_VELOCITY){
			this.vertical_speed = TERMINAL_VELOCITY;
		}
		if(this.move(0, this.vertical_speed * Main.getDeltaTime())){
			if(this.vertical_speed < 0){
				this.onGround = true;
			}
			this.vertical_speed = 0;
		}else{
			this.onGround = false;
		}
	}
	
	public void update(){
		if(this.hasGravity){
			this.fall();
		}
	}
}
