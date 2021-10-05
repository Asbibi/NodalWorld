package gamelogic;

/**
* Abstract representation of a terrain model.
* Its only role is to determine if a given position contains surface or not.
*  
* @see TerrainNode
*/ 
public interface TerrainModel {

	/**
	* @param pos 
	* @return true if there is a surface element at the given position, otherwise false
	*/ 
	public abstract boolean hasSurfaceAt(Vec2D pos);

}
