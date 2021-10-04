package gamelogic;

/**
* Abstract class to initialize the game manager with some datas.
* Depending on the initializer it can be species, surfaces, entites, terrain, rules or even frame...
* 
* @see GameManager
*/ 
public abstract class GameManagerInitializer {
	/**
	* Initialize the given game manager with some data
	* @param the GameManager to intialize
	*/ 
	public abstract void initManager(GameManager manager);
}
