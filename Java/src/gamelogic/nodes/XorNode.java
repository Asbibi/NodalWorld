package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to calculate the eXclusive-OR (XOR) on two booleans. <br/>
* 
* Inputs : a, b <br/>
* Outputs : a^b
* 
*/
public class XorNode extends Node {

	/**
	*
	*/ 
	public XorNode() {
		super("Xor");
		addInput(new Input("a", Boolean.class));
		addInput(new Input("b", Boolean.class));
		addOutput(new Output("a^b", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Boolean val1 = getInput("a").getData(Boolean.class);
		Boolean val2 = getInput("b").getData(Boolean.class);
		getOutput("a^b").setData(val1 ^ val2);
	}

}
