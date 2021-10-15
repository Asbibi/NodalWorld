package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* A terrain model used to mask a terrain model with another one (substraction).
*/ 
public class TerrainModelMask implements TerrainModel {

	private TerrainModel layer, mask;

	/**
	* @param layer the terrain model to mask
	*/ 
	public void setLayer(TerrainModel layer) {
		this.layer = layer;
	}

	/**
	* @param mask the terrain model used as mask
	*/ 
	public void setMask(TerrainModel mask) {
		this.mask = mask;
	}

	/**
	* @param pos
	* @return true if the given position is inside layer and outside mask
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (layer == null || mask == null)
			return false;
		
		return (layer.hasSurfaceAt(pos) && !mask.hasSurfaceAt(pos));
	}

}