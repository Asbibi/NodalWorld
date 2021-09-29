package game;

import javax.swing.JOptionPane;

import gameinterface.GameFrame;
import gamelogic.GameManager;

public class Main {
	static GameManager gameManager;
	static GameFrame gameFrame;
	

	public static void main(String[] args) {
		gameManager = new GameManager();
		gameFrame = new GameFrame(gameManager);
		gameFrame.setVisible(true);
	}

	/*
	 * something i found that may be useful later : 
	           JOptionPane.showMessageDialog(null, "message to display in a new window");
	 */
}
