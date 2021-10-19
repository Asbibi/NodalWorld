package gamelogic.nodes;

import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelRandom;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.NetworkIOException;

/**
* The node model used to create a randomized terrain layer. <br/>
* 
* Inputs : surface, seed, density <br/>
* Outputs : layer
* 
*/
public class TerrainNodeRandom extends TerrainNode<TerrainModelRandom> {

	public TerrainNodeRandom() {
		super("Terrain Random", new TerrainModelRandom());
		addInput(new Input("seed", Integer.class));
		addInput(new Input("density", Double.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		Integer seed = getInput("seed").getData(Integer.class);
		model.setSeed(seed);

		Double density = getInput("density").getData(Double.class);
		model.setDensity(density);
	}

}
