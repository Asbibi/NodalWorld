package game;

import javax.swing.JOptionPane;

import gameinterface.GameFrame;

public class Main {
	static GameFrame gameFrame;
	

	public static void main(String[] args) {
		gameFrame = new GameFrame();
		// link frame to game
		gameFrame.setVisible(true);
	}

	/*
	 * something i found that may be useful later : 
	           JOptionPane.showMessageDialog(null, "message to display in a new window");
	 */
}
