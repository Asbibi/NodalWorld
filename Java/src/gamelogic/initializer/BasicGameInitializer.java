package gamelogic.initializer;

import java.awt.Color;

import gamelogic.GameManager;
import gamelogic.GameManagerInitializer;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainLayer;
import gamelogic.Vec2D;

public class BasicGameInitializer extends GameManagerInitializer {
	private int width;
	private int height;
	private int surfaceIndex;
	
	
	public BasicGameInitializer(int w, int h) {
		this(w, h, -1);
	}
	public BasicGameInitializer(int w, int h, int surfaceIndexToUse) {
		width = w;
		height = h;
		surfaceIndex = surfaceIndexToUse;
	}
	
	@Override
	public void initManager(GameManager manager) {
		
		Surface ground = new Surface("Ground", 		"res/Tile_Dirt.png");
		Surface water = new Surface("Water", 		"res/Tile_Water.png");
		Surface grassD = new Surface("Grass Dark", 	"res/Tile_Grass.png");
		Surface grassB = new Surface("Grass Bright", 	"res/Tile_GrassBright.png");		
		ground.setColor(new Color(196,161,126));
		water.setColor(new Color(57,68,166));
		grassD.setColor(new Color(42,98,61));
		grassB.setColor(new Color(50,182,98));
		manager.addSurface(ground);
		manager.addSurface(water);
		manager.addSurface(grassD);
		manager.addSurface(grassB);

		manager.addSpecies(new Species("Human", 	"res/Animal_Human.png"));
		manager.addSpecies(new Species("Chicken", 	"res/Animal_Chicken.png"));
		manager.addSpecies(new Species("Boar", 		"res/Animal_Boar.png"));
		manager.addSpecies(new Species("Birch", 	"res/Tree_Birch.png"));
		manager.addSpecies(new Species("Oak", 		"res/Tree_Oak.png"));
		
		TerrainLayer firstLayer = new TerrainLayer(width, height);
		firstLayer.fill(manager.getSurface(surfaceIndex));
		manager.pushTerrain(firstLayer);		
	}
}
