package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

public class TerrainModelEllipse implements TerrainModel {

	private Vec2D center;
	private int radiusX, radiusY;

	public void setCenter(Vec2D center) {
		this.center = center;
	}

	public void setRadiusX(int rx) {
		radiusX = rx;
	}

	public void setRadiusY(int ry) {
		radiusY = ry;
	}

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
