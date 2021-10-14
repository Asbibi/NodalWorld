package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to get the species being processed. <br/>
* 
* Inputs : <br/>
* Outputs : species
* 
*/
public class CurSpeciesNode extends Node {

	/**
	*
	*/ 
	public CurSpeciesNode() {
		super("Current Species");
		addOutput(new Output("species", Species.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("species").setData(game.getCurrentSpecies());
	}

}
