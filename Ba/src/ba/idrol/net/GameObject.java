package ba.idrol.net;

import static org.lwjgl.opengl.GL11.*;


public class GameObject {
	// Stores current size, position and sprite.
	protected int width, height;
	public float x, y;
	protected Sprite texture;
	
	// Stores Gravity, terminal velocity(max speed) and vertical speed.
	protected static float GRAVITY = 0.04f;
	protected static float TERMINAL_VELOCITY = 2f;
	protected float vertical_speed = 0;
	
	// Indicates if player is on ground.
	protected boolean onGround = false;
	
	// Stores direction.
	public boolean direction = LEFT;
	public static final boolean RIGHT = false, LEFT = true;
	
	/*
	 * Creates new object with specified size and position.
	 */
	public GameObject(int width, int height, float x, float y){
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		Main.currentGameComponent.addGameObject(this);;
		
	}
	
	/*
	 * Creates new object with specified size, position and sprite.
	 */
	public GameObject(int width, int height, Sprite sprite, float x, float y){
		this(width, height, x, y);
		this.texture = sprite;
	}
	/*
	 * Creates new object with specified sprite.
	 */
	public GameObject(Sprite sprite){
		this(sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite, 0, 0);
	}
	
	/*
	 * Creates new object with specified position and sprite.
	 */
	public GameObject(Sprite sprite, float x, float y){
		this((int)sprite.getTexture().getTextureWidth(), sprite.getTexture().getTextureHeight(), sprite, x, y);
	}
	
	
	/*
	 * Gets the top of the collision box.
	 */
	protected float getTop(){
		return this.y+this.height;
	}
	
	/*
	 * Gets the bottom of the collision box.
	 */
	protected float getBot(){
		return this.y;
	}
	
	/*
	 * Gets the left side of the collision box.
	 */
	protected float getLeft(){
		return this.x;
	}
	
	/*
	 * Gets the right side of the collision box.
	 */
	protected float getRight(){
		return this.x+this.width;
	}
	
	/*
	 * Render method for object.
	 */
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
	
	/*
	 * Update method for object.
	 */
	public void update(){
		
	}
	
	/*
	 * Removes object.
	 */
	public void destroy(){
		Main.currentGameComponent.objList.remove(this);
	}
	
	/*
	 * Sets the texture.
	 */
	public void setTexture(Sprite sprite) {
		this.texture = sprite;
	}
}
