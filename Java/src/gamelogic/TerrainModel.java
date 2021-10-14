package gamelogic;

import java.io.Serializable;

/**
* Abstract representation of a terrain model.
* Its only role is to determine if a given position contains surface or not.
*  
* @see TerrainNode
*/ 
public interface TerrainModel extends Serializable {

	/**
	* @param pos 
	* @return true if there is a surface element at the given position, otherwise false
	*/ 
	public boolean hasSurfaceAt(Vec2D pos);

}
