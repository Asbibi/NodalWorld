package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* The point of this model is to easily have a completely filled TerrainSlot
*/ 
public class TerrainModelFill implements TerrainModel {

	/**
	* @param pos
	* @return true
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		return true;
	}
}
