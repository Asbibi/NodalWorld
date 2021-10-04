package game;

import javax.swing.JOptionPane;

import gameinterface.GameFrame;
import gamelogic.GameManager;

public class Main {
	static GameManager gameManager;
	static GameFrame gameFrame;
	

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
		
		gameManager = new GameManager(width, height);
		gameFrame = new GameFrame(gameManager);
	}

	/*
	 * something i found that may be useful later : 
	           JOptionPane.showMessageDialog(null, "message to display in a new window");
	 */
}
