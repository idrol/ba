package ba.idrol.net;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import static org.lwjgl.opengl.GL11.*;

/*
 * The games sprite class that create sprites
 */
public class Sprite {
	
	// The stored texture.
	private Texture texture;
	
	/*
	 * Creates new sprite with specified image location.
	 */
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
	
	/*
	 * Binds the sprite.
	 */
	public void bind(){
		this.texture.bind();
	}
	
	/*
	 * Get the texture of the sprite.
	 */
	public Texture getTexture(){
		return this.texture;
	}
}
