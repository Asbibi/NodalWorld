package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.util.Random;

/**
* The node model used to generate a random integer in the range [0, bound[. <br/>
* 
* Inputs : seed, bound <br/>
* Outputs : res
* 
*/
public class RandIntNode extends Node {

	private Random rand;

	/**
	* 
	*/ 
	public RandIntNode() {
		super("Random Int");
		Input input = new Input("seed", Integer.class);
		input.setManualValue(-1);
		addInput(input);
		rand = new Random();
		addInput(new Input("bound", Integer.class));
		addOutput(new Output("res", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int seed = getInput("seed").getData(Integer.class);
		Random randUsed = seed < 0 ? rand : new Random(getInput("seed").getData(Integer.class));
		
		Integer bound = getInput("bound").getData(Integer.class);
		Integer val = randUsed.nextInt(bound);
		getOutput("res").setData(val);
	}

}
