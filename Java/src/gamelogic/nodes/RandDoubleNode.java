package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;
import gamelogic.NetworkIOException;

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
		super("Random Double");
		rand = new Random();
		addOutput(new Output("val", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Double val = rand.nextDouble();
		getOutput("val").setData(val);
	}

}
