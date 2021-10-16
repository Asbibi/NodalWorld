package gamelogic.nodes;

import gamelogic.NetworkIOException;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelFill;

/**
* The node model used to have a completely filled up layer. <br/>
* 
* Inputs : surface<br/>
* Outputs : layer
* 
*/
public class TerrainNodeFill extends TerrainNode<TerrainModelFill> {

	public TerrainNodeFill() {
		super("Filled Terrain", new TerrainModelFill());
	}

	@Override
	public void initModel() throws NetworkIOException {
	}
}
