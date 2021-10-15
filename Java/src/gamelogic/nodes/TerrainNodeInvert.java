package gamelogic.nodes;

import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.TerrainModel;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelInvert;

/**
* The node model used to join two terrain layers. <br/>
* 
* Inputs : surface, layer <br/>
* Outputs : layer
* 
*/
public class TerrainNodeInvert extends TerrainNode<TerrainModelInvert> {

	public TerrainNodeInvert() {
		super("Invert Terrain", new TerrainModelInvert());
		addInput(new Input("layer", TerrainModel.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		TerrainModel layer = getInput("layer").getData(TerrainModel.class);
		model.setLayer(layer);
	}
}
