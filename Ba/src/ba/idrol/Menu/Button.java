package ba.idrol.Menu;

import org.lwjgl.input.Mouse;

import ba.idrol.net.GameObject;
import ba.idrol.net.Sprite;

public class Button extends GameObject {
	private boolean hover = false;
	public Button(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
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
