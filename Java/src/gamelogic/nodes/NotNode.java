package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

/**
* The node model used to negate a boolean. <br/>
* 
* Inputs : val <br/>
* Outputs : res
* 
* @see GameManager
*/
public class NotNode extends Node {

	/**
	*
	*/ 
	public NotNode() {
		super("Not");
		addInput(new Input("val", Boolean.class));
		addOutput(new Output("res", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Boolean val = getInput("val").getData(Boolean.class);
		getOutput("res").setData(!val);
	}

}
