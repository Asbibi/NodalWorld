package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to double value corresponding to the division of an integer with another one. <br/>
* 
* Inputs : a, b <br/>
* Outputs : a/b
* 
*/
public class DivIntNode extends Node {

	/**
	*
	*/ 
	public DivIntNode() {
		super("Divide : Int");
		addInput(new Input("a", Integer.class));
		Input input = new Input("b", Integer.class);
		input.setManualValue(1);
		addInput(input);		
		addOutput(new Output("a/b", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		double b = getInput("b").getData(Integer.class);
		if (b == 0) {
			getOutput("a/b").setData(null);
			return;
		}
		getOutput("a/b").setData((getInput("a").getData(Integer.class)).doubleValue() / b);
	}


}
