package game;

import java.util.ArrayList;

import game.initializer.BasicGameInitializer;
import gameinterface.GameFrame;
import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainLayer;
import gamelogic.TerrainStack;
import gamelogic.Vec2D;

public class TestGameWorldInterface {
	static GameManager gameManager;
	static GameFrame gameFrame;
	
	static int W = 10;	//20;
	static int H = 10;	//15;	
	
	

	public static void main(String[] args) {
		gameManager = new GameManager(new BasicGameInitializer(W,H));
		

		/*TerrainLayer waterLayer = new TerrainLayer(W,H);
		for (int i = 0; i< W; i++)
			waterLayer.setSurfaceAt(new Vec2D(i,0), gameManager.getSurface(1));
		gameManager.pushTerrain(waterLayer);
		
		birch.addMemberAt(new Vec2D(5,5), 5);
		birch.addMemberAt(new Vec2D(5,5), 5);
		birch.addMemberAt(new Vec2D(6,5), 5);
		birch.addMemberAt(new Vec2D(8,9), 5);
		birch.addMemberAt(new Vec2D(8,9), 5);
		birch.addMemberAt(new Vec2D(8,9), 5);
		human.addMemberAt(new Vec2D(3,5), 5);
		human.addMemberAt(new Vec2D(3,5), 5);
		human.addMemberAt(new Vec2D(5,1), 5);
		human.addMemberAt(new Vec2D(8,9), 5);
		human.addMemberAt(new Vec2D(8,9), 5);*/
		

		gameFrame = new GameFrame(gameManager);
		System.out.println(gameManager.arraysToString());
		System.out.println(gameManager.toString());
		
		gameFrame.gWP_test().update(0);
		
		gameFrame.setVisible(true);
	}

}
