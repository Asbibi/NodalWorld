package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to calculate the cosine of an angle. <br/>
* 
* Inputs : t <br/>
* Outputs : cos(t)
* 
*/
public class TrigoCosNode extends Node {


	/**
	*
	*/ 
	public TrigoCosNode() {
		super("Cosinus");
		addInput(new Input("t", Double.class));	
		addOutput(new Output("cos(t)", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("cos(t)").setData(Math.cos(getInput("t").getData(Double.class)));
	}

}
