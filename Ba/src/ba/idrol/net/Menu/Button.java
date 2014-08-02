package ba.idrol.net.Menu;

import org.lwjgl.input.Mouse;

import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

/*
 * Button class used in menu to define clicable buttons.
 */
public class Button extends GameObject {
	// Indicates hover event.
	private boolean hover = false;
	
	/*
	 * Creates button with specified spriat and position
	 */
	public Button(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	/*
	 * @see ba.idrol.net.GameObject#update()
	 * 
	 * Update loop for button.
	 */
	@Override
	public void update(){
		int x = Mouse.getX(), y = Mouse.getY();
		if(this.x < x && this.x+this.width > x && this.y < y && this.y+this.height > y){
			this.hover = true;
		}else{
			this.hover = false;
		}
		if(Mouse.isButtonDown(0) && this.hover){
			this.buttonClicked();
		}
	}
	
	protected void buttonClicked(){
		
	}

}
