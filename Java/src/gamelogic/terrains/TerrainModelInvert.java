package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* Invert the surface occupied by a TerrainModel.
*/ 
public class TerrainModelInvert  implements TerrainModel {

	private TerrainModel layer;

	/**
	* @param layer the terrain model to invert
	*/ 
	public void setLayer(TerrainModel layer) {
		this.layer = layer;
	}

	/**
	* @param pos
	* @return true if the given position is outside the terrain model
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (layer == null)
			return false;
		
		return (!layer.hasSurfaceAt(pos));
	}
}
