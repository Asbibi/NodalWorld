package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;

import java.util.Random;

/**
* The node model used to generate a random number in the range [0, 1[. <br/>
* 
* Inputs : none <br/>
* Outputs : val
* 
* @see GameManager
*/
public class RandDoubleNode extends Node {

	private Random rand;

	/**
	* 
	*/ 
	public RandDoubleNode() {
		super();
		rand = new Random();
		addOutput(new Output("val", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Double val = rand.nextDouble();
		getOutput("val").setData(val);
	}

}
