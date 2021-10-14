package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* A terrain model that represents a rectangular area.
*/ 
public class TerrainModelRectangle implements TerrainModel {

	private Vec2D corner, dimensions;

	/**
	* @param corner the top-left corner of the rectangular area
	*/ 
	public void setCorner(Vec2D corner) {
		this.corner = corner;
	}

	/**
	* @param dimensions the dimensions of the rectangular area
	*/ 
	public void setDimensions(Vec2D dimensions) {
		this.dimensions = dimensions;
	}

	/**
	* @param pos
	* @return true if the given position is inside the rectangular area, otherwise false
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (corner == null || dimensions == null)
			return false;
		
		return (pos.getX() >= corner.getX() 
				&& pos.getX() < corner.getX()+dimensions.getX() 
				&& pos.getY() >= corner.getY() 
				&& pos.getY() < corner.getY()+dimensions.getY());
	}

}
