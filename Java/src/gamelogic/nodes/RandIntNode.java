package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

import java.util.Random;

/**
* The node model used to generate a random integer in the range [0, N[. <br/>
* 
* Inputs : none <br/>
* Outputs : val
* 
* @see GameManager
*/
public class RandIntNode extends Node {

	private Random rand;

	/**
	* @param bound
	*/ 
	public RandIntNode() {
		super("Random Int");
		rand = new Random();
		addInput(new Input("bound", 10));
		addOutput(new Output("val", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Integer bound = getInput("bound").getData(Integer.class);
		Integer val = rand.nextInt(bound);
		getOutput("val").setData(val);
	}

}
