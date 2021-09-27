package game;

import gameinterface.GameFrame;

public class Main {
	static GameFrame gameFrame;
	

	public static void main(String[] args) {
		gameFrame = new GameFrame();
		// link frame to game
		gameFrame.setVisible(true);
	}

}
