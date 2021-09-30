package game;

import gameinterface.GameFrame;
import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.TerrainLayer;
import gamelogic.Vec2D;
import gamelogic.initializer.BasicGameInitializer;
import gamelogic.initializer.TestLayerInitializer;

public class TestGameInterface {
	static GameManager gameManager;
	static GameFrame gameFrame;
	
	static int W = 15;
	static int H = 15;	
	
	

	public static void main(String[] args) {
		gameManager = new GameManager(new TestLayerInitializer(W,H));
		
		TerrainLayer waterLayer = new TerrainLayer(W,H);
		for (int i = 0; i< W; i++)
			waterLayer.setSurfaceAt(new Vec2D(i,0), gameManager.getSurface(1));
		gameManager.pushTerrain(waterLayer);
		
		Species birch = gameManager.getSpecies(3);
		Species human = gameManager.getSpecies(0);
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
		human.addMemberAt(new Vec2D(8,9), 5);
		
		gameFrame = new GameFrame(gameManager);
		
		System.out.println(gameManager.arraysToString());
		System.out.println(gameManager.toString());
	}

}
