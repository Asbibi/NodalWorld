package gamelogic.initializer;

import gamelogic.GameManager;
import gamelogic.GameManagerInitializer;
import gamelogic.Surface;
import gamelogic.TerrainLayer;

/**
* The default initializer class.
* If constructed with terrain dimension will create a terrainlayer with the correct dimension filled up with empty surfaces.
* Otherwise do nothing.
* 
* @see GameManagerInitializer
*/ 
public class EmptyGameInitializer extends GameManagerInitializer {
	private int width;
	private int height;
	
	public EmptyGameInitializer() {
		this(-1,-1);
	}
	public EmptyGameInitializer(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public void initManager(GameManager manager) {
		if (width < 0 || height < 0)
			return;
		
		TerrainLayer emptyLayer = new TerrainLayer(width, height);
		emptyLayer.fill(Surface.getEmpty());
		manager.pushTerrain(emptyLayer);
	}
}
