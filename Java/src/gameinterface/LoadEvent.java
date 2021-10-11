package gameinterface;

import java.awt.event.ActionEvent;

import gamelogic.GameManager;

public class LoadEvent extends ActionEvent {
	private GameManager loadedManager;

	public LoadEvent(Object source,GameManager loadedManager) {
		super(source, ActionEvent.ACTION_FIRST, "LoadManager");
		this.loadedManager = loadedManager;
	}
	
	public GameManager getLoadedManager() {
		return loadedManager;
	}
}
