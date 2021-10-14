package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to get the game's grid dimensions. <br/>
* 
* Inputs : <br/>
* Outputs : width, height
* 
*/
public class DimNode extends Node {

	/**
	*
	*/ 
	public DimNode() {
		super("World Dimensions");
		addOutput(new Output("width", Integer.class));
		addOutput(new Output("height", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("width").setData(game.gridWidth());
		getOutput("height").setData(game.gridHeight());
	}

}
