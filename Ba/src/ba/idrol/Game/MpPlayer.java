package ba.idrol.Game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.HashMap;
import java.util.Map;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.net.Sprites;
import ba.idrol.network.client.MpPlayerData;

public class MpPlayer extends Player {
	public int id;
	public static Map<Integer, MpPlayerData> createPlayersQue = new HashMap<Integer, MpPlayerData>();
	public boolean attacking = false;
	private Sprite sword;
	private float swordRot = 0;
	public MpPlayer(float x, float y){
		super(Sprites.get("player"), x, y);
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	
	public void setSword(Sprite sprite){
		this.sword = sprite;
	}
	
	@Override
	public void update(){
		if(this.attacking){
			this.swordRot += 0.5f*Main.getDeltaTime();
			if(this.swordRot > 140 || this.swordRot < -140){
				this.swordRot = 0;
			}
		}
	}
	
	public void render(){
		super.render();
//		System.out.println(this.attacking);
		if(this.attacking){
			this.sword.bind();
			glPushMatrix();
				glTranslatef(this.x, this.y+16, 0);
				glTranslatef(16, 0, 0);
				if(this.direction == LEFT){
					glRotatef(-this.swordRot, 0, 0, 1);
				}else{
					glRotatef(this.swordRot, 0, 0, 1);
				}
				glTranslatef(-16, 0, 0);
				glBegin(GL_QUADS);
					glTexCoord2f(0, 1);
					glVertex2f(0, 0);
					glTexCoord2f(1, 1);
					glVertex2f(this.sword.getTexture().getTextureWidth(), 0);
					glTexCoord2f(1, 0);
					glVertex2f(this.sword.getTexture().getTextureWidth(), this.sword.getTexture().getTextureHeight());
					glTexCoord2f(0, 0);
					glVertex2f(0, this.sword.getTexture().getTextureHeight());
				glEnd();
			glPopMatrix();
		}
	}
	
	public static void createPlayers(){
//		System.out.println(createPlayersQue.size());
		if(createPlayersQue.size() > 0){
			for(MpPlayerData player : createPlayersQue.values()){
				MpPlayer newPlayer = (MpPlayer) new MpPlayer(player.x, player.y);
				newPlayer.id = player.id;
				System.out.println("Adding mpplayer with id: "+newPlayer.id);
				Game.players.put(newPlayer.id, newPlayer);
				createPlayersQue.remove(player.id);
			}
		}
	}
	

}
