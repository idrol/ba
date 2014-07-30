package ba.idrol.server;

import java.util.ArrayList;
import java.util.List;


public class GameObject {
	protected int width, height;
	public float x, y;
	
	protected static float GRAVITY = 0.04f;
	protected static float TERMINAL_VELOCITY = 2f;
	protected float vertical_speed = 0;
	protected boolean onGround = false;
	protected boolean hasGravity = false;
	protected boolean hasColisionBox = true;
	
	// ClientSide Sprite to use
	public String spriteName = "";
	
	public static List<GameObject> objList = new ArrayList<GameObject>();
	public static List<GameObject> networkedObjects = new ArrayList<GameObject>();
	
	public GameObject(int width, int height, float x, float y){
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		objList.add(this);
	}
	public GameObject(int width, int height, float x, float y, String spriteName){
		this(width, height, x, y);
		this.spriteName = spriteName;
		networkedObjects.add(this);
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
	public boolean move(float x){
		float newx = this.x + x;
		if(!this.checkForCollision(newx, this.y)){
			this.x = newx;
			return true;
		}
		return false;
	}
	
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
	
	public void fall(){
		this.vertical_speed -= GRAVITY;
		if(this.vertical_speed > TERMINAL_VELOCITY){
			this.vertical_speed = TERMINAL_VELOCITY;
		}
		if(this.checkForCollision(this.x, this.y + this.vertical_speed * BaServer.global_delta)){
			if(this.vertical_speed < 0){
				this.onGround = true;
			}
			this.vertical_speed = 0;	
		}else{
			this.y += this.vertical_speed * BaServer.global_delta;
			this.onGround = false;
		}
	}
	
	public void update(){
		if(this.hasGravity){
			this.fall();
		}
	}
	public void destroy(){
		objList.remove(this);
	}
}
