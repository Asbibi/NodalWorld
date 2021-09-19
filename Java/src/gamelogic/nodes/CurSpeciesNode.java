package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.Output;

/**
* The node model used to get the species being processed. <br/>
* 
* Inputs : none <br/>
* Outputs : species
* 
* @see GameManager
*/
public class CurSpeciesNode extends Node {

	/**
	*
	*/ 
	public CurSpeciesNode() {
		super();
		addOutput(new Output("species", Species.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		getOutput("species").setData(game.getCurrentSpecies());
	}

}
