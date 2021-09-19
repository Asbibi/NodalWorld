package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

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
		super();
		addInput(new Input("val1", Boolean.class));
		addInput(new Input("val2", Boolean.class));
		addOutput(new Output("res", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Boolean val1 = getInput("val1").getData(Boolean.class);
		Boolean val2 = getInput("val2").getData(Boolean.class);
		getOutput("res").setData(val1 && val2);
	}

}
