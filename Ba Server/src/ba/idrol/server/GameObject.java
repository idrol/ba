package ba.idrol.server;

import java.util.ArrayList;
import java.util.List;


public class GameObject {
	
	// Stores object size.
	protected int width, height;
	// Stores object positions.
	public float x, y;
	// Stores current gravity and terminal velocity(max fall speed).
	protected static float GRAVITY = 0.04f, TERMINAL_VELOCITY = 2f;
	// Stores the current vertical speed.
	protected float vertical_speed = 0;
	// onGround indicates if player is touching the ground.
	// hasGravity indicates if graviti is enabled.
	protected boolean onGround = false, hasGravity = false;
	// Indicates if object should collide.
	protected boolean hasColisionBox = true;
	
	// ClientSide Sprite to use
	public String spriteName = "";
	
	// Stores a list of all existing objects.
	public static List<GameObject> objList = new ArrayList<GameObject>();
	// Stores a list of all objects that are available to the clients note non networked objects still have collision.
	public static List<GameObject> networkedObjects = new ArrayList<GameObject>();
	
	/*
	 * Creates new GameObject with specified size and position.
	 */
	public GameObject(int width, int height, float x, float y){
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		objList.add(this);
	}
	/*
	 * Creates new GameObject with specified size, position and spriteName.
	 * 
	 * If this constructor is used object will be networked because there's a spriteName to call on client side.
	 */
	public GameObject(int width, int height, float x, float y, String spriteName){
		this(width, height, x, y);
		this.spriteName = spriteName;
		networkedObjects.add(this);
	}
	
	/*
	 * Enables gravity for the object.
	 */
	public GameObject enableGravity(){
		this.hasGravity = true;
		return this;
	}
	
	/*
	 * Disables collision for the object.
	 */
	public GameObject disableCollision(){
		this.hasColisionBox = false;
		return this;
	}
	
	/*
	 * returns true if collision is enabled otherwise false.
	 */
	public boolean isCollisionEnabled(){
		return this.hasColisionBox;
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
	 * Method that calculates the new position and detects if it collides.
	 * 
	 * Returns false if it collides
	 * 
	 * Returns true if it could move.
	 */
	public boolean move(float x){
		float newx = this.x + x;
		if(!this.checkForCollision(newx, this.y)){
			this.x = newx;
			return true;
		}
		return false;
	}
	
	/*
	 * Checks if object would collide att given position.
	 */
	public boolean checkForCollision(float x, float y){
		for(GameObject obj: objList){
			if(!obj.equals(this)){
				if(obj.hasColisionBox){
					if(x < obj.x + obj.width &&
					   x + this.width > obj.x &&
					   y < obj.y + obj.height &&
					   this.height + y > obj.y){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * Method to simulate falling
	 * Only called if gravity is enabled for object.
	 */
	public void fall(){
		this.vertical_speed -= GRAVITY;
		if(this.vertical_speed > TERMINAL_VELOCITY){
			this.vertical_speed = TERMINAL_VELOCITY;
		}
		if(this.checkForCollision(this.x, this.y + this.vertical_speed * BaServer.getDeltaTime())){
			if(this.vertical_speed < 0){
				this.onGround = true;
			}
			this.vertical_speed = 0;	
		}else{
			this.y += this.vertical_speed * BaServer.getDeltaTime();
			this.onGround = false;
		}
	}
	
	/*
	 * Updates the game object.
	 */
	public void update(){
		if(this.hasGravity){
			this.fall();
		}
	}
	
	/*
	 * This method should be called when object is destroyed.
	 */
	public void destroy(){
		objList.remove(this);
	}
}
