package ba.idrol.net.util;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import static org.lwjgl.opengl.GL11.*;

public class TextInput extends GameObject {
	
	// Stores weather the input field should listen for enter events
	private boolean isActive = false;
	// Stores weather this input field is focused and should listen for input
	private boolean isFocused = false;
	// Stores the entered string
	private String text = "";
	// Time when render state of enter was changed
	private long lastTextEnter, lastRemove;
	private boolean renderTextEnter = true;
	private Sprite textEnter;
	private int maxLength = 20;
	private TrueTypeFont arial;
	
	public TextInput(int width, int height, float x, float y) {
		super(width, height, x, y);
		this.textEnter = new Sprite("/res/images/text_enter.png");
		this.lastTextEnter = Main.getTime();
		this.lastRemove = Main.getTime();
		Font awtFont = new Font("Arial", Font.BOLD, 24);
		this.arial = new TrueTypeFont(awtFont, false);
	}

	@Override
	public void render(){
		if(this.isActive){
			glDisable(GL_TEXTURE_2D);
			glPushMatrix();
				glColor4f(0.1f, 0.1f, 0.1f, 0.6f);
				glTranslatef(this.x, this.y , 0);
				glBegin(GL_QUADS);
					glVertex2f(0, this.height);
					glVertex2f(this.width, this.height);
					glVertex2f(this.width, 0);
					glVertex2f(0, 0);
				glEnd();
				glColor4f(1, 1, 1, 1);
			glPopMatrix();
			glEnable(GL_TEXTURE_2D);
			this.arial.drawString(this.x, this.y, this.text, Color.white);
		}
		if(this.isFocused){
			if(this.renderTextEnter){
				glPushMatrix();
					glColor4f(0.1f, 0.1f, 0.1f, 0.6f);
					this.textEnter.bind();
					glBegin(GL_QUADS);
						glTexCoord2f(0, 1);
						glVertex2f(0, 0);
						glTexCoord2f(1, 1);
						glVertex2f(20, 0);
						glTexCoord2f(1, 0);
						glVertex2f(20, 20);
						glTexCoord2f(0, 0);
						glVertex2f(0, 20);
					glEnd();
					glColor4f(1, 1, 1, 1);
				glPopMatrix();
			}
		}
	}
	
	/*
	 * @see ba.idrol.net.GameObject#update()
	 * 
	 * Update and checks for input for the text field.
	 */
	@Override
	public void update(){
		if((this.lastTextEnter + 500) <= Main.getTime()){
			if(this.renderTextEnter){
				this.renderTextEnter = false;
			}else{
				this.renderTextEnter = true;
			}
			this.lastTextEnter = Main.getTime();
		}
		if(this.isActive){
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				this.isFocused = true;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				this.isFocused = false;
			}
		}
		if(Keyboard.next()){
			if(this.isFocused){
				// Only run for key down events
				if(Keyboard.getEventKeyState()){
					if(Keyboard.getEventKey() == Keyboard.KEY_BACK || Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == Keyboard.KEY_LSHIFT){
					}else{
						System.out.println(Keyboard.getKeyName(Keyboard.getEventKey())+", "+Keyboard.getEventCharacter());
						if(!(this.text.length() >= this.maxLength)){
							this.text += Keyboard.getEventCharacter();
						}
					}
				}
			}
		}
		if(this.isFocused){
			if(Keyboard.isKeyDown(Keyboard.KEY_BACK)){
				if(this.lastRemove + 150 <= Main.getTime()){
					this.text = removeLastChar(this.text);
					this.lastRemove = Main.getTime();
				}
			}
		}
	}
	
	public String removeLastChar(String str) {
	    if (str.length() > 0) {
	      str = str.substring(0, str.length()-1);
	    }
	    return str;
	}
	
	public void enable(){
		this.isActive = true;
	}
	
	public void disable(){
		this.isActive = false;
	}
	
	public void setMaxLength(int length){
		this.maxLength = length;
	}
}
