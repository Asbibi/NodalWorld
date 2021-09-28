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

	private int bound;
	private Random rand;

	/**
	* @param bound
	*/ 
	public RandIntNode(int bound) {
		super("Random Int" + bound);
		this.bound = bound;
		rand = new Random();
		addOutput(new Output("val", Integer.class));
	}

	/**
	* @return the right bound of the random number generator range
	*/ 
	public int getBound() {
		return bound;
	}

	/**
	* @param b
	*/ 
	public void setBound(int b) {
		bound = b;
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Integer val = rand.nextInt(bound);
		getOutput("val").setData(val);
	}

}
