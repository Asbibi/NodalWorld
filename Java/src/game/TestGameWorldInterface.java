package game;

import java.util.ArrayList;

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
		gameManager = new GameManager(W,H);
		
		Surface ground = new Surface("Ground", "res/Tile_Dirt.png");
		Surface water = new Surface("Water", "res/Tile_Water.png");
		Surface grass = new Surface("Grass Dark", "res/Tile_Grass.png");
		Surface grassBright = new Surface("Grass Bright", "res/Tile_GrassBright.png");
		gameManager.addSurface(ground);
		gameManager.addSurface(water);
		gameManager.addSurface(grass);
		gameManager.addSurface(grassBright);

		Species human = new Species("Human", "res/Animal_Human.png");
		Species chicken = new Species("Chicken", "res/Animal_Chicken.png");
		Species birch = new Species("Birch", "res/Tree_Birch.png");
		gameManager.addSpecies(human);
		gameManager.addSpecies(birch);
		gameManager.addSpecies(chicken);
		
		TerrainLayer firstLayer = new TerrainLayer(W,H);
		firstLayer.fill(ground);
		TerrainLayer secondLayer = new TerrainLayer(W,H);
		for (int i = 0; i< W; i++)
			secondLayer.setSurfaceAt(new Vec2D(i,0), water);
		gameManager.pushTerrain(firstLayer);
		gameManager.pushTerrain(secondLayer);
		
		ArrayList<Species> species = new ArrayList<Species>();
		species.add(human);
		species.add(birch);
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
		
		gameFrame.gWP_test().update(0);
		
		gameFrame.setVisible(true);
	}

}
