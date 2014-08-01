package ba.idrol.net;

import java.util.HashMap;
import java.util.Map;

/*
 * Class the registers client sprites with name that server uses.
 */
public class Sprites {
	// Stores all sprites.
	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	/*
	 * Adds sprite.
	 */
	public static void addSprite(Sprite sprite, String name){
		sprites.put(name, sprite);
	}
	
	/*
	 * Gets sprite.
	 */
	public static Sprite get(String name) {
		return sprites.get(name);
	}
}
