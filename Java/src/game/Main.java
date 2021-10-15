package game;

import gameinterface.GameFrame;
import gameinterface.NewWorldDialogBox;
import gamelogic.GameManager;
import gamelogic.GameManagerBuilder;
import gamelogic.Saver;

/**
* Class starting the game by opening a NewWorldDialogBox to create a GameManager, and a GameFrame and a GamePlayer linked to it respectively to display and update over time the GameManager.<br/>
* It's possible to skip the NewWorldDialogBox by launching the app with at least 2 arguments being a width and a height. It will then create a "Basic" GameManager with the given width and height. 
* 
* @see NewWorldDialogBox
* @see GameManager
* @see GameManagerBuilder
* @see GameFrame
* @see GamePlayer
*/ 
public class Main {
	
	/**
	* Static main method that will start the game
	* @param args if the 2 first arguments are a width and a height (both int), they will be used to create a "Basic" gamemanager, and ignore otherwhise
	*/ 
	public static void main(String[] args) {
		GameManager gameManager = getGameManager(args);
		if (gameManager == null)
			return;
		
		GameFrame gameFrame = new GameFrame(gameManager);
		GamePlayer gamePlayer = new GamePlayer(gameManager, gameFrame);
	}
	
	/**
	* This method will check args to determines if it's a width and a height.<br/>
	* If args is valid, it will return a "Basic" GameManager built with the given width and height.<br/><br/>
	* 
	* Otherwise, the app will start a NewWorldDialogBox to make the user choose his starting template.<br/>
	* The method will then return the corresponding GameManager. It will return null if the user cancel his action, i.e. if getConfirm() on the NewWorldDialogBox returns false.
	* 
	* @param args if the 2 first arguments are a width and a height (both int), they will be used to create a "Basic" gamemanager, and ignore otherwhise
	* @return the gameManager that corresponds to args if valid, or to the user template in the other case
	*/ 
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
		}
		else {
			NewWorldDialogBox dialogBox = new NewWorldDialogBox(null);
			if (!dialogBox.getConfirm())
				return null;
			
			return GameManagerBuilder.buildGameFromTemplate(dialogBox.getSelectedTemplate(), dialogBox.getTemplateWidth(), dialogBox.getTemplateHeight(), dialogBox.getSelectedSaveFilePath());
		}
	}
}
