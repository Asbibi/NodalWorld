package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to negate a boolean. <br/>
* 
* Inputs : a <br/>
* Outputs : !a
* 
*/
public class NotNode extends Node {

	/**
	*
	*/ 
	public NotNode() {
		super("Not");
		addInput(new Input("a", Boolean.class));
		addOutput(new Output("!a", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Boolean val = getInput("a").getData(Boolean.class);
		getOutput("!a").setData(!val);
	}

}
