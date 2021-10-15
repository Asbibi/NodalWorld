package gamelogic.nodes;

import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.TerrainModel;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelIntersection;

/**
* The node model used to intersect two terrain layers. <br/>
* 
* Inputs : surface, layer1, layer2 <br/>
* Outputs : layer
* 
*/
public class TerrainNodeIntersect extends TerrainNode<TerrainModelIntersection> {

	public TerrainNodeIntersect() {
		super("Intersect Terrain", new TerrainModelIntersection());
		addInput(new Input("layer 1", TerrainModel.class));
		addInput(new Input("layer 2", TerrainModel.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		TerrainModel layer = getInput("layer 1").getData(TerrainModel.class);
		model.setLayer1(layer);

		TerrainModel mask = getInput("layer 2").getData(TerrainModel.class);
		model.setLayer2(mask);
	}
}
