package ba.idrol.net;

import java.util.ArrayList;
import java.util.List;

/*
 * Game component class.
 * 
 * Defines a game state on which to run for example Menu state or Game State.
 */
public class GameComponent {
	/*
	 * Object for component.
	 */
	protected List<GameObject> objList = new ArrayList<GameObject>();
	
	/*
	 * Load objects.
	 */
	public void loadObjects(){
		
	}
	
	/*
	 * Update loop
	 */
	public void update(int delta){
		
	}
	
	/*
	 * Render loop.
	 */
	public void render(){
		
	}
	
	/*
	 * adds game object.
	 */
	public void addGameObject(GameObject obj){
		this.objList.add(obj);
	}
	
	/*
	 * Returns game object list.
	 */
	public List<GameObject> getGameObjectList(){
		return this.objList;
	}

}
