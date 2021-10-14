package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to divide a double with another one. <br/>
* 
* Inputs : a, b <br/>
* Outputs : a/b
* 
*/
public class DivDoubleNode extends Node {

	/**
	*
	*/ 
	public DivDoubleNode() {
		super("Divide : Double");
		addInput(new Input("a", Double.class));
		Input input = new Input("b", Double.class);
		input.setManualValue(1.);
		addInput(input);		
		addOutput(new Output("a/b", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		double b = getInput("b").getData(Double.class);
		if (b == 0) {
			getOutput("a/b").setData(null);
			return;
		}
		getOutput("a/b").setData(getInput("a").getData(Double.class) / b);
	}


}
