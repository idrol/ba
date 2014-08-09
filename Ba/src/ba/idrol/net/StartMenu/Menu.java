package ba.idrol.net.StartMenu;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.newdawn.slick.Color;

import ba.idrol.net.GameComponent;
import ba.idrol.net.GameObject;
import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Lobby.Lobby;
import ba.idrol.net.util.TextInput;

/*
 * The menu game component.
 */
public class Menu extends GameComponent {
	
	// Stores Background and buttons.
	private Login loginBtn;
	private Exit exitBtn;
	private GameObject bg, logo, cloud_1, cloud_2, cloud_3, cloud_4, cloud_5, cloud_6, cloud_7, cloud_8, cloud_9, cloud_10;
	public static TextInput playerName;
	public static TextInput password;
	private static boolean displayError = false;
	private static String errorMsg = "";
	private static Text text = new Text();
	
	/*
	 * @see ba.idrol.net.GameComponent#loadObjects()
	 * Creates background and buttons.
	 */
	@Override
	public void loadObjects(){
		Sprite cloud = new Sprite("/res/images/world/cloud.png");
		bg = new GameObject(800, 600, new Sprite("/res/images/world/bg.png"), 0, 0);
		
		cloud_1 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_2 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_3 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_4 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_5 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_6 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_7 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_8 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_9 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		cloud_10 = new GameObject(cloud, Main.randomGen(800), Main.randomGen(600));
		
		logo = new GameObject(512, 128, new Sprite("res/images/menu/logo.png"), 190, 52);
		loginBtn = new Login(new Sprite("/res/images/menu/login.png"), 270, 270);
		playerName = new TextInput(200, 40, 300, 170);
		playerName.enable();
		password = new TextInput(200, 40, 300, 220);
		password.enable();
		playerName.setTabAction(password);
		password.setTabAction(playerName);
		password.setEnterAction(loginBtn);
	}
	
	/*
	 * @see ba.idrol.net.GameComponent#update(int)
	 * Menu update loop
	 */
	@Override
	public void update(int delta){
		for(GameObject obj: objList){
			obj.update();
		}
	}
	
	/*
	 * @see ba.idrol.net.GameComponent#render()
	 * Menu render loop.
	 */
	@Override
	public void render(){
		for(GameObject obj: objList){
			obj.render();
		}
//		Font.renderWord("Idrol is the best", 100, 100, 20);
		playerName.render();
		password.render();
		if(displayError){
			text.render(250, 340, errorMsg, Color.red);
		}
	}

	public static void switchToLobby() {
		if(Main.currentUser != null){
			try {
				Lobby lobby = new Lobby();
				if(lobby.connect()){
					Main.currentGameComponent = lobby;
					Main.currentGameComponent.loadObjects();
				}else{
					errorMsg = "Could not connect to lobby server!";
					displayError = true;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public static void displayFailedLoggin() {
		errorMsg = "Wrong username/password!";
		displayError = true;
	}
}
