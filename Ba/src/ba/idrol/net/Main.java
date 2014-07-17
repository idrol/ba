package ba.idrol.net;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import ba.idrol.Game.Game;
import ba.idrol.Menu.Menu;
import static org.lwjgl.opengl.GL11.*;

public class Main {
	private float x = 400, y = 300;
	private float rotation = 0;
	private long lastFrame;
	private int fps;
	private long lastFPS;
	static int deltaTime;
	public static GameComponent currentGameComponent;

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL();
		Main.currentGameComponent = new Menu();
		Main.currentGameComponent.loadObjects();
		getDelta();
		lastFPS = getTime();
		
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
		Main.currentGameComponent.update(delta);
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
		
		               
        
		
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
	}

	public void renderGL() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Main.currentGameComponent.render();
	}
	
	public static void main(String[] argv) {
		Main main = new Main();
		main.start();
	}
}