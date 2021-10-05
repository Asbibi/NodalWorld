package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

public class TerrainModelRectangle implements TerrainModel {

	private Vec2D corner, dimensions;

	public void setCorner(Vec2D corner) {
		this.corner = corner;
	}

	public void setDimensions(Vec2D dimensions) {
		this.dimensions = dimensions;
	}

	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		return (pos.getX() >= corner.getX() 
				&& pos.getX() < corner.getX()+dimensions.getX() 
				&& pos.getY() >= corner.getY() 
				&& pos.getY() < corner.getY()+dimensions.getY());
	}

}
