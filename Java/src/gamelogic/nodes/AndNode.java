package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to perform logical AND. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public class AndNode extends Node {

	/**
	*
	*/ 
	public AndNode() {
		super("And");
		addInput(new Input("a", Boolean.class));
		addInput(new Input("b", Boolean.class));
		addOutput(new Output("a&b", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Boolean val1 = getInput("a").getData(Boolean.class);
		Boolean val2 = getInput("b").getData(Boolean.class);
		getOutput("a&b").setData(val1 && val2);
	}

}
