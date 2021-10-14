package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* A terrain model that represents an elliptic area.
*/ 
public class TerrainModelEllipse implements TerrainModel {

	private Vec2D center;
	private int radiusX, radiusY;

	/**
	* @param center the center of the elliptic area
	*/ 
	public void setCenter(Vec2D center) {
		this.center = center;
	}

	/**
	* @param rx the radius along the x-axis
	*/ 
	public void setRadiusX(int rx) {
		radiusX = rx;
	}

	/**
	* @param ry the radius along the y-axis
	*/ 
	public void setRadiusY(int ry) {
		radiusY = ry;
	}

	/**
	* @param pos
	* @return true if the given position is inside the elliptic area, otherwise false
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (center == null || radiusX <= 0 || radiusY <= 0)
			return false;
		
		Vec2D v = Vec2D.sub(pos, center);
		return ((float) (v.getX()*v.getX()) / (float) (radiusX*radiusX) 
				+ (float) (v.getY()*v.getY()) / (float) (radiusY*radiusY)
				<= 1.f);
	}

}
