package ba.idrol.net;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
	float x = 400, y = 300;
	float rotation = 0;
	long lastFrame;
	int fps;
	long lastFPS;
	static int deltaTime;
	Player plr;
	GameObject obj;

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL();
		getDelta();
		lastFPS = getTime();
		try {
			plr = new Player(new Sprite("/res/images/character/char.png"), 30, 50);
			obj = new GameObject(800, 10, new Sprite("/res/no_texture.png"), 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!Display.isCloseRequested()) {
			deltaTime = getDelta();
			
			update(deltaTime);
			renderGL();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}
	
	public void update(int delta) {
		
		for(GameObject obj: GameObject.objList){
			obj.update();
		}
		
		updateFPS();
	}
	
	private int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	public static int getDeltaTime() {
		return deltaTime;
	}
	
	
	
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public void initGL() {
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);               
        
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
        
        	// enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        	GL11.glViewport(0,0,800,600);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void renderGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		for(GameObject obj: GameObject.objList){
			obj.render();
		}
	}
	
	public static void main(String[] argv) {
		Main main = new Main();
		main.start();
	}
}