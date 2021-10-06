package game;

import gameinterface.GameFrame;
import gamelogic.GameManager;
import gamelogic.GameManagerBuilder;

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
		
		GameManager game = GameManagerBuilder.buildBasicGame(width, height);
		GameFrame window = new GameFrame(game);
		GamePlayer player = new GamePlayer(game, window);

		window.setVisible(true);
	}
}
