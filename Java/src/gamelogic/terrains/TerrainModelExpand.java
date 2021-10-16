package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* Increases the area of the terrain given in argument: a position will be included in the new terrain if it or any of its direct neighbors is included in the original terrain.<br/>
* Each position has 4 neighbors : up, down, left and right.
*/ 
public class TerrainModelExpand implements TerrainModel {

	private TerrainModel layer;

	/**
	* @param layer the terrain model to invert
	*/ 
	public void setLayer(TerrainModel layer) {
		this.layer = layer;
	}

	/**
	* @param pos
	* @return true if the given position is in the original terrain model or its border
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (layer == null)
			return false;
		
		int x = pos.getX();
		int y = pos.getY();
		
		return ( layer.hasSurfaceAt(new Vec2D(x,y)) ||
				layer.hasSurfaceAt(new Vec2D(x-1,y)) ||
				layer.hasSurfaceAt(new Vec2D(x+1,y)) ||
				layer.hasSurfaceAt(new Vec2D(x,y-1)) ||
				layer.hasSurfaceAt(new Vec2D(x,y+1)));
	}
}
