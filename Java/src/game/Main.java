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

	static GameManager gameManager;
	static GameFrame gameFrame;

	static TimerTask task = new TimerTask() {
		@Override
		public void run() {
			int frame = gameManager.evolveGameState();
			gameFrame.updateWorld(frame);
			//System.out.println(gameManager);
		}
	};
	
	
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

		gameFrame.addPauseActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(task, 1000, 1000);
			}
		});
	}
}
