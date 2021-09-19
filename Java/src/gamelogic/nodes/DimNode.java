package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;

/**
* The node model used to get the game's grid dimensions. <br/>
* 
* Inputs : none <br/>
* Outputs : width, height
* 
* @see GameManager
*/
public class DimNode extends Node {

	/**
	*
	*/ 
	public DimNode() {
		super();
		addOutput(new Output("width", Integer.class));
		addOutput(new Output("height", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		getOutput("width").setData(game.gridWidth());
		getOutput("height").setData(game.gridHeight());
	}

}
