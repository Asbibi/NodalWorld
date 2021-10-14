package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to calculate the sine of an angle. <br/>
* 
* Inputs : t <br/>
* Outputs : sin(t)
* 
*/
public class TrigoSinNode  extends Node {

	/**
	* 
	*/ 
	public TrigoSinNode() {
		super("Sinus");
		addInput(new Input("t", Double.class));	
		addOutput(new Output("sin(t)", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("sin(t)").setData(Math.sin(getInput("t").getData(Double.class)));
	}

}
