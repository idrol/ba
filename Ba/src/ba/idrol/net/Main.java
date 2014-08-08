package ba.idrol.net;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ba.idrol.net.StartMenu.Menu;
import static org.lwjgl.opengl.GL11.*;

/*
 * Main client class.
 */
public class Main {
	
	// Error statuses.
	public static final int FATAL_ERROR = 1;
	public static final int SHUTDOWN = 0;
	public static final int WARNING = 2;
	public static final int LOG = 0;
	
	
	// Stores times for last fream and fps count.
	private long lastFrame, lastFPS;
	// Stores fps
	private int fps;
	// Stores delta time
	private static int deltaTime;
	// Stores the current active game Component.
	public static GameComponent currentGameComponent;
	public static User currentUser;
	
	/*
	 * Starts the game.
	 */
	public void start() {
		// Create the display.
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		// Initialize opengl
		initGL();
		// Set the current game component to menu and load.
		Main.currentGameComponent = new Menu();
		Main.currentGameComponent.loadObjects();
		
		// Get the delta and start fps count.
		getDelta();
		lastFPS = getTime();
		
		// Main Game Loop
		while (!Display.isCloseRequested()) {
			
			update(deltaTime);
			renderGL();
			deltaTime = getDelta();
//			System.out.println("Client delta time: "+deltaTime);
			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}
	
	public static void setCurrentUser(User user){
		currentUser = user;
	}
	
	/*
	 * Main Update loop.
	 */
	public void update(int delta) {
		Main.currentGameComponent.update(delta);
		updateFPS();
	}
	
	/*
	 * Get client delta.
	 */
	private int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/*
	 * Get current set delta time.
	 */
	public static int getDeltaTime() {
		return deltaTime;
	}
	
	/*
	 * Exit the game.
	 */
	public static void destroy(int status){
		Display.destroy();
		System.exit(status);
	}
	
	/*
	 * Get current time in milliseconds.
	 */
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/*
	 * update the fps.
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	/*
	 * Initialize opengl.
	 */
	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 600, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/*
	 * Renders the game.
	 */
	public void renderGL() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Main.currentGameComponent.render();
	}
	
	/*
	 * Random gen.
	 */
	public static int randomGen(int range){
			int randomInt = (int) (range * Math.random());
			return randomInt;
	}

	/*
	 * Log function.
	 */
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
	
	public static void main(String[] argv) {
		Main main = new Main();
		main.start();
	}
}