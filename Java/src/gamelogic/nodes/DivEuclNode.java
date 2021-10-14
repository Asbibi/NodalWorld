package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to compute the euclidean division of an integer (dividend) by another integer (divisor). <br/>
* 
* Inputs : a, b <br/>
* Outputs : a/b, remainder
* 
*/
public class DivEuclNode extends Node {

	/**
	*
	*/ 
	public DivEuclNode() {
		super("Divide : int (Euclidean)");
		addInput(new Input("a", Integer.class));
		Input input = new Input("b", Integer.class);
		input.setManualValue(1);
		addInput(input);
		addOutput(new Output("a/b", Integer.class));
		addOutput(new Output("remainder", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int dividend = getInput("a").getData(Integer.class);
		int divisor = getInput("b").getData(Integer.class);
		if (divisor == 0) {
			getOutput("a/b").setData(null);
			getOutput("remainder").setData(null);
			return;
		}
		getOutput("a/b").setData(dividend/divisor);
		getOutput("remainder").setData(dividend%divisor);
	}

}
