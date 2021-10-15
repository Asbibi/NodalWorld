package gameinterface;

import java.awt.event.ActionEvent;

import gamelogic.GameManager;

/**
* A simple ActionEvent that carries a gameManager reference, used to trigger ActionListeners after loading a savefile. 
* 
* @see GameManager
*/ 
public class LoadEvent extends ActionEvent {
	private GameManager loadedManager;

	/**
	* @param ObjectSource
	* @param loadedManager the reference of the loaded GameManager
	*/
	public LoadEvent(Object source, GameManager loadedManager) {
		super(source, ActionEvent.ACTION_FIRST, "LoadManager");
		this.loadedManager = loadedManager;
	}
	
	/**
	* @return the loaded GameManager
	*/
	public GameManager getLoadedManager() {
		return loadedManager;
	}
}
