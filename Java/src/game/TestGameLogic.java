package game;

import gamelogic.GameManager;
import gamelogic.Vec2D;

public class TestGameLogic {

	public static void main(String[] args) {
		int width = 5;
		int height = 5;
		GameManager game = new GameManager(width, height);

		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				Vec2D pos = new Vec2D(x, y);
				System.out.print(game.surfaceAt(pos).getName());
				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}

}
