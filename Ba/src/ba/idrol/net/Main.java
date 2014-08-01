package ba.idrol.net;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ba.idrol.Menu.Menu;
import static org.lwjgl.opengl.GL11.*;

public class Main {
	
	public static final int FATAL_ERROR = 1;
	public static final int SHUTDOWN = 0;
	public static final int WARNING = 2;
	public static final int LOG = 0;
	
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
			
			update(deltaTime);
			renderGL();
			deltaTime = getDelta();
			System.out.println("Client delta time: "+deltaTime);
			Display.update();
			Display.sync(120);
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
	
	public static void destroy(int status){
		Display.destroy();
		System.exit(status);
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
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public void renderGL() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Main.currentGameComponent.render();
	}
	
	public static void main(String[] argv) {
		Main main = new Main();
		main.start();
	}
	

	public static int randomGen(int range){
			int randomInt = (int) (range * Math.random());
			return randomInt;
	}

	public static void log(int status, String msg) {
		String prefix = "";
		switch(status){
		case Main.FATAL_ERROR:
			prefix = "[FATAL]";
			break;
		case Main.WARNING:
			prefix = "[WARNING]";
			break;
		case Main.LOG:
			prefix = "[LOG]";
			break;
		}
		System.out.println("[Client]"+prefix+msg);
	}
}