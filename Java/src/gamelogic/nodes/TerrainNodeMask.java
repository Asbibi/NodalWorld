package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerrainNode;
import gamelogic.TerrainModel;
import gamelogic.terrains.TerrainModelMask;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.NetworkIOException;

public class TerrainNodeMask extends TerrainNode<TerrainModelMask> {

	public TerrainNodeMask() {
		super("Masked Terrain", new TerrainModelMask());
		addInput(new Input("layer", TerrainModel.class));
		addInput(new Input("mask", TerrainModel.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		TerrainModel layer = getInput("layer").getData(TerrainModel.class);
		model.setLayer(layer);

		TerrainModel mask = getInput("mask").getData(TerrainModel.class);
		model.setMask(mask);
	}

}
