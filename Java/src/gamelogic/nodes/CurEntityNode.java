package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Entity;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to get the entity being processed. <br/>
* 
* Inputs : <br/>
* Outputs : entity
* 
*/
public class CurEntityNode extends Node {

	/**
	*
	*/ 
	public CurEntityNode() {
		super("Current Entity");
		addOutput(new Output("entity", Entity.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("entity").setData(game.getCurrentEntity());
	}

}
