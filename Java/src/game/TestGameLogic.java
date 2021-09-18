package game;

import gamelogic.GameManager;
import gamelogic.Vec2D;

public class TestGameLogic {

	public static void main(String[] args) {
		int width = 5;
		int height = 5;
		GameManager game = new GameManager(width, height);
		System.out.print(game);
	}

}
