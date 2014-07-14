package ba.idrol.net;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	
	private Texture texture;
	
	public Sprite(String path) throws IOException{
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
	}
	
	public void bind(){
		this.texture.bind();
	}
	public Texture getTexture(){
		return this.texture;
	}
}
