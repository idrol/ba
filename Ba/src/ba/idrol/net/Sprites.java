package ba.idrol.net;

import java.util.HashMap;
import java.util.Map;

public class Sprites {
	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	public static void addSprite(Sprite sprite, String name){
		sprites.put(name, sprite);
	}
	public static Sprite get(String name) {
		return sprites.get(name);
	}
}
