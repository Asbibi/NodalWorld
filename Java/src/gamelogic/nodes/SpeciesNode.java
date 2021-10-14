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
* Outputs : period, count, id
* 
*/
public class SpeciesNode extends Node {

	public SpeciesNode() {
		super("Species Data");
		addInput(new Input("species", Species.class));
		addOutput(new Output("period", Integer.class));
		addOutput(new Output("count", Integer.class));
		addOutput(new Output("id", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Species sp = getInput("species").getData(Species.class);
		getOutput("count").setData(sp.getMembers().size());
		getOutput("period").setData(sp.getTriggerTime());
		getOutput("id").setData(sp.getId());
	}

}
