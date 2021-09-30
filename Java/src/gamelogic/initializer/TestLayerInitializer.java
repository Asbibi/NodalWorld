package gamelogic.initializer;

import java.awt.Color;

import gamelogic.GameManager;
import gamelogic.GameManagerInitializer;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainLayer;
import gamelogic.Vec2D;

public class TestLayerInitializer extends GameManagerInitializer {
	private int width;
	private int height;
	
	
	
	public TestLayerInitializer(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public void initManager(GameManager manager) {
		
		Surface ground = new Surface("Ground", 		"res/Tile_Dirt.png");
		Surface water = new Surface("Water", 		"res/Tile_Water.png");
		Surface grassD = new Surface("Grass Dark", 	"res/Tile_Grass.png");
		Surface grassB = new Surface("Grass Bright", 	"res/Tile_GrassBright.png");
		manager.addSurface(ground);
		manager.addSurface(water);
		manager.addSurface(grassD);
		manager.addSurface(grassB);
		
		ground.setColor(new Color(196,161,126));
		water.setColor(new Color(57,68,166));
		grassD.setColor(new Color(42,98,61));
		grassB.setColor(new Color(50,182,98));

		manager.addSpecies(new Species("Human", 	"res/Animal_Human.png"));
		manager.addSpecies(new Species("Chicken", 	"res/Animal_Chicken.png"));
		manager.addSpecies(new Species("Boar", 		"res/Animal_Boar.png"));
		manager.addSpecies(new Species("Birch", 	"res/Tree_Birch.png"));
		manager.addSpecies(new Species("Oak", 		"res/Tree_Oak.png"));
		
		TerrainLayer firstLayer = new TerrainLayer(width, height);
		firstLayer.fill(water);
		for (int x = 2; x < width-2; x++) {

			for (int y = 2; y < height-2; y++) {
				firstLayer.setSurfaceAt(new Vec2D(x,y), ground);
			}
		}
		for (int x = 0; x < width; x++) {

			for (int y = 0; y < height; y++) {
				if (x==y)
					firstLayer.setSurfaceAt(new Vec2D(x,y), grassB);				
			}
		}

		for (int x = 0; x < width; x++) {
			firstLayer.setSurfaceAt(new Vec2D(x,0), Surface.getEmpty());
		}
		
		
		TerrainLayer grassLayer = new TerrainLayer(width-1,height-1);
		grassLayer.fill(grassD);
		for (int x = 2; x < 8; x++) {
			for (int y = 2; y < 8; y++) {
				grassLayer.setSurfaceAt(new Vec2D(x,y), grassB);
			}
		}
		grassLayer.setSurfaceAt(new Vec2D(4,4), Surface.getEmpty());
		grassLayer.setSurfaceAt(new Vec2D(4,5), Surface.getEmpty());
		grassLayer.setSurfaceAt(new Vec2D(4,3), Surface.getEmpty());
		grassLayer.setSurfaceAt(new Vec2D(5,4), Surface.getEmpty());
		grassLayer.setSurfaceAt(new Vec2D(3,4), Surface.getEmpty());
		
		TerrainLayer thinGrassLayer = new TerrainLayer(2,height);
		thinGrassLayer.fill(grassB);
		
		TerrainLayer waterLayer = new TerrainLayer(width,height);
		for (int i = 0; i< width; i++)
			waterLayer.setSurfaceAt(new Vec2D(i,0), water);
		
		manager.pushTerrain(firstLayer);
		manager.pushTerrain(waterLayer);	
		manager.pushTerrain(grassLayer);	
		manager.pushTerrain(thinGrassLayer);	
	}
}
