package game.initializer;

import gamelogic.GameManager;
import gamelogic.GameManagerInitializer;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainLayer;
import gamelogic.Vec2D;

public class BasicGameInitializer extends GameManagerInitializer {
	private int width = 10;
	private int height = 10;
	
	public BasicGameInitializer(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public void initManager(GameManager manager) {
		
		manager.addSurface(new Surface("Ground", 		"res/Tile_Dirt.png"));
		manager.addSurface(new Surface("Water", 		"res/Tile_Water.png"));
		manager.addSurface(new Surface("Grass Dark", 	"res/Tile_Grass.png"));
		manager.addSurface(new Surface("Grass Bright", 	"res/Tile_GrassBright.png"));

		manager.addSpecies(new Species("Human", 	"res/Animal_Human.png"));
		manager.addSpecies(new Species("Chicken", 	"res/Animal_Chicken.png"));
		manager.addSpecies(new Species("Boar", 		"res/Animal_Boar.png"));
		manager.addSpecies(new Species("Birch", 	"res/Tree_Birch.png"));
		manager.addSpecies(new Species("Oak", 		"res/Tree_Oak.png"));
		
		TerrainLayer groundLayer = new TerrainLayer(width, height);
		groundLayer.fill(manager.getSurface(0));
		manager.pushTerrain(groundLayer);
		
	}
}
