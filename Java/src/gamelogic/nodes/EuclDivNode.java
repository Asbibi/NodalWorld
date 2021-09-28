package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

/**
* The node model used to compute the euclidean division of an integer (dividend) by another integer (divisor). <br/>
* 
* Inputs : dividend, divisor <br/>
* Outputs : quotient, remainder
* 
* @see GameManager
*/
public class EuclDivNode extends Node {

	/**
	*
	*/ 
	public EuclDivNode() {
		super("Euclidean Division");
		addInput(new Input("dividend", Integer.class));
		addInput(new Input("divisor", Integer.class));
		addOutput(new Output("quotient", Integer.class));
		addOutput(new Output("remainder", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Integer dividend = getInput("dividend").getData(Integer.class);
		Integer divisor = getInput("divisor").getData(Integer.class);
		getOutput("quotient").setData(dividend/divisor);
		getOutput("remainder").setData(dividend%divisor);
	}

}
