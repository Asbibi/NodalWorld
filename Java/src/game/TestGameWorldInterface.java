package game;

import java.util.ArrayList;

import gameinterface.GameFrame;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainLayer;
import gamelogic.TerrainStack;
import gamelogic.Vec2D;

public class TestGameWorldInterface {
	static GameFrame gameFrame;
	
	static int W = 10;	//20;
	static int H = 10;	//15;
	
	static Surface ground;
	static Surface water;
	static Species human;
	static Species birch;
	static TerrainStack terrain;
	
	

	public static void main(String[] args) {
		gameFrame = new GameFrame();
		
		ground = new Surface("Ground", "res/Tile_Dirt.png");
		water = new Surface("Water", "res/Tile_Water.png");		

		human = new Species("Human", "res/Animal_Human.png");
		birch = new Species("Birch", "res/Tree_Birch.png");
		
		terrain = new TerrainStack();
		TerrainLayer firstLayer = new TerrainLayer(W,H);
		firstLayer.fill(ground);
		TerrainLayer secondLayer = new TerrainLayer(W,H);
		for (int i = 0; i< W; i++)
			secondLayer.setSurfaceAt(new Vec2D(i,0), water);
		terrain.pushTerrain(firstLayer);
		terrain.pushTerrain(secondLayer);
		
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
		
		
		gameFrame.gWP_test().updateTerrain(terrain);
		gameFrame.gWP_test().updateAllSpeciesDisplayed(species);
		
		gameFrame.setVisible(true);
	}

}
