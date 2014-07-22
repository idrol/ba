package ba.idrol.net;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	
	private Texture texture;
	
	public Sprite(String path){
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch (RuntimeException | IOException  e) {
			try {
				this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/no_texture.png"));
				e.printStackTrace();
				Main.log(Main.LOG, "Could not load texture for ("+path+").");
			} catch (RuntimeException | IOException e1) {
				e1.printStackTrace();
				Main.log(Main.FATAL_ERROR, "Game encounterd an texture error!");
				Main.destroy(Main.FATAL_ERROR);
			}
		}
	}
	
	public void bind(){
		this.texture.bind();
	}
	public Texture getTexture(){
		return this.texture;
	}
}
