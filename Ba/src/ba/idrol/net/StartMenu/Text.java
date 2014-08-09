package ba.idrol.net.StartMenu;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import static org.lwjgl.opengl.GL11.*;

public class Text {
	
	private TrueTypeFont arial;
	
	public Text(){
		Font awtFont = new Font("Arial", Font.PLAIN, 24);
		this.arial = new TrueTypeFont(awtFont, false);
	}
	public void render(float x, float y, String text, Color color){
		this.arial.drawString(x, y, text, color);
		glColor4f(1,1,1,1);
	}
}
