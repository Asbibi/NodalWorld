package gamelogic.nodes;

import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.TerrainModel;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelExpand;

/**
* The node model used to expand another model. <br/>
* 
* Inputs : surface, layer <br/>
* Outputs : layer
* 
*/
public class TerrainNodeExpand extends TerrainNode<TerrainModelExpand> {

	public TerrainNodeExpand() {
		super("Expand Terrain", new TerrainModelExpand());
		addInput(new Input("layer", TerrainModel.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		TerrainModel layer = getInput("layer").getData(TerrainModel.class);
		model.setLayer(layer);
	}
}
