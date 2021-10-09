package game;

import gameinterface.GameFrame;
import gameinterface.NewWorldDialogBox;
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
		GameManager gameManager = getGameManager(args);
		if (gameManager == null)
			return;
		
		GameFrame gameFrame = new GameFrame(gameManager);
		GamePlayer gamePlayer = new GamePlayer(gameManager, gameFrame);
	}
	
	
	private static GameManager getGameManager(String[] args) {
		int width=-1;
		int height=-1;
		if (args.length > 1) {
			System.out.println(args.length);
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				width = 10;
				height = 10;
			}
			return GameManagerBuilder.buildBasicGame(width, height);
			//return GameManagerBuilder.buildFullLoadedGame("/savetest.nws", 10,10);
		}
		else {
			NewWorldDialogBox dialogBox = new NewWorldDialogBox(null);
			if (!dialogBox.getConfirm())
				return null;
			
			return GameManagerBuilder.buildGameFromTemplate(dialogBox.getSelectedTemplate(), dialogBox.getTemplateWidth(), dialogBox.getTemplateHeight(), dialogBox.getSelectedSaveFilePath());
		}
	}
}
