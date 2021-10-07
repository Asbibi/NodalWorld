package game;

import gameinterface.GameFrame;
import gamelogic.GameManager;
import gamelogic.GameManagerBuilder;
import gamelogic.Saver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Timer;
import java.util.TimerTask;

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
	
		gameManager = GameManagerBuilder.buildBasicGame(width, height);
		//gameManager = GameManagerBuilder.buildFullLoadedGame("/savetest.nws");
		gameFrame = new GameFrame(gameManager);
		GamePlayer gamePlayer = new GamePlayer(gameManager, gameFrame);

		gameFrame.addSaveActionListener( e -> Saver.saveGame("/savetest", gameManager, true) );
		window.setVisible(true);
	}
}
