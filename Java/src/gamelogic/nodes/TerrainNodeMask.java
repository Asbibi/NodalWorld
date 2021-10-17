package gamelogic.nodes;

import gamelogic.TerrainNode;
import gamelogic.TerrainModel;
import gamelogic.terrains.TerrainModelMask;
import gamelogic.Input;
import gamelogic.NetworkIOException;

/**
* The node model used to mask a terrain layer with another one. <br/>
* 
* Inputs : surface, layer, mask <br/>
* Outputs : layer
* 
*/
public class TerrainNodeMask extends TerrainNode<TerrainModelMask> {

	public TerrainNodeMask() {
		super("Substract Terrain", new TerrainModelMask());
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
