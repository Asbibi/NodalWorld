package gamelogic.nodes;

import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.TerrainModel;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelErode;

/**
* The node model used to erode another model. <br/>
* 
* Inputs : surface, layer <br/>
* Outputs : layer
* 
*/
public class TerrainNodeErode extends TerrainNode<TerrainModelErode> {

	public TerrainNodeErode() {
		super("Erode Terrain", new TerrainModelErode());
		addInput(new Input("layer", TerrainModel.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		TerrainModel layer = getInput("layer").getData(TerrainModel.class);
		model.setLayer(layer);
	}
}
