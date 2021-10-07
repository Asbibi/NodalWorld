package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Species;
import gamelogic.NetworkIOException;

/**
* The node model used to extract data from a species. <br/>
* 
* Inputs : species <br/>
* Outputs : size
* 
* @see GameManager
* @see Species
*/
public class SpeciesNode extends Node {

	public SpeciesNode() {
		super("Species Data");
		addInput(new Input("species", Species.class));
		addOutput(new Output("size", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Species sp = getInput("species").getData(Species.class);
		getOutput("size").setData(sp.getMembers().size());
	}

}
