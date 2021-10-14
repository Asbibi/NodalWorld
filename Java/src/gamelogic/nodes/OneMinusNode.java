package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
/**
* The node model used to compute 1-x for any double x. <br/>
* 
* Inputs : x <br/>
* Outputs : 1-x
* 
*/
public class OneMinusNode extends Node {


	/**
	*
	*/ 
	public OneMinusNode() {
		super("One minus double");
		addInput(new Input("x", Double.class));
		addOutput(new Output("1-x", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("1-x").setData(1 - getInput("x").getData(Double.class));
	}


}
