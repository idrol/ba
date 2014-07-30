package ba.idrol.Game;

import java.util.HashMap;
import java.util.Map;

import ba.idrol.net.Sprite;
import ba.idrol.net.Sprites;
import ba.idrol.network.client.MpPlayerData;

public class MpPlayer extends Player {
	public int id;
	public static Map<Integer, MpPlayerData> createPlayersQue = new HashMap<Integer, MpPlayerData>();
	public MpPlayer(float x, float y){
		super(Sprites.get("player"), x, y);
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
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
