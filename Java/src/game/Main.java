package game;

import gameinterface.GameFrame;
import gamelogic.GameManager;
import gamelogic.GameManagerBuilder;

/**
* Main instancier of the game
* 
* @see ControlPanel, WorldPanel
*/ 
public class Main {
	static GameManager gameManager;
	static GameFrame gameFrame;
	
	
	/**
	* Static main method that will instanciate the gamemanager and a gameframe linked to it
	*/ 
	public static void main(String[] args) {
		int width = 10;
		int height = 10;
		if (args.length > 1) {
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				width = 10;
				height = 10;
			}
		}		
		
		gameManager = GameManagerBuilder.buildBasicGame(width, height);
		gameFrame = new GameFrame(gameManager);
	}
}
