package ba.idrol.net;

import java.util.ArrayList;
import java.util.List;

public class GameComponent {
	protected List<GameObject> objList = new ArrayList<GameObject>();
	
	public void loadObjects(){
		
	}
	public void update(int delta){
		
	}
	public void render(){
		
	}
	
	public void addGameObject(GameObject obj){
		this.objList.add(obj);
	}
	public List<GameObject> getGameObjectList(){
		return this.objList;
	}

}
