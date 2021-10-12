package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

public class TerrainModelMask implements TerrainModel {

	private TerrainModel layer, mask;

	public void setLayer(TerrainModel layer) {
		this.layer = layer;
	}

	public void setMask(TerrainModel mask) {
		this.mask = mask;
	}

	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		if (layer == null || mask == null)
			return false;
		
		return (layer.hasSurfaceAt(pos) && mask.hasSurfaceAt(pos));
	}

}