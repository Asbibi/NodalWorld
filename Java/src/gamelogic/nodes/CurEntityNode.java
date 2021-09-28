package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Entity;
import gamelogic.Output;

/**
* The node model used to get the entity being processed. <br/>
* 
* Inputs : none <br/>
* Outputs : entity
* 
* @see GameManager
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
	public void evaluate(GameManager game) {
		getOutput("entity").setData(game.getCurrentEntity());
	}

}
