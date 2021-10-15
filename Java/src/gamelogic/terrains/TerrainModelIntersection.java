package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* Result in the intersection of 2 TerrainModels.
*/ 
public class TerrainModelIntersection implements TerrainModel {

	private TerrainModel layer1, layer2;

	/**
	* @param layer the terrain model to use as first TerrainModel
	*/ 
	public void setLayer1(TerrainModel layer) {
		this.layer1 = layer;
	}

	/**
	* @param layer the terrain model to use as second TerrainModel
	*/ 
	public void setLayer2(TerrainModel layer) {
		this.layer2 = layer;
	}

	/**
	* @param pos
	* @return true if the given position is inside both terrain models
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (layer1 == null || layer2 == null)
			return false;
		
		return (layer1.hasSurfaceAt(pos) && layer2.hasSurfaceAt(pos));
	}
}
