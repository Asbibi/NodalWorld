package game;

import gameinterface.GameFrame;
import gamelogic.GameManager;
import gamelogic.GameManagerBuilder;
import gamelogic.Saver;

/**
* Main instancier of the game
* 
* @see ControlPanel
* @see WorldPanel
*/ 
public class Main {
	
	/**
	* Static main method that will instanciate the gamemanager and a gameframe linked to it
	*/ 
	public static void main(String[] args) {
		int width=-1;
		int height=-1;
		if (args.length > 1) {
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				width = 10;
				height = 10;
			}
		}
	
		GameManager gameManager = GameManagerBuilder.buildBasicGame(width, height);
		//GameManager gameManager = GameManagerBuilder.buildFullLoadedGame("/savetest.nws");
		GameFrame gameFrame = new GameFrame(gameManager);
		GamePlayer gamePlayer = new GamePlayer(gameManager, gameFrame);

		gameFrame.addSaveActionListener( e -> Saver.saveGame("/savetest", gameManager, true) );
		gameFrame.setVisible(true);
	}
}
