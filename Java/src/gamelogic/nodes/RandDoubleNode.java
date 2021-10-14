package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.util.Random;

/**
* The node model used to generate a random number in the range [0, 1[. <br/>
* 
* Inputs : seed <br/>
* Outputs : red
* 
*/
public class RandDoubleNode extends Node {

	private Random rand;

	/**
	* 
	*/ 
	public RandDoubleNode() {
		super("Random Double");
		Input input = new Input("seed", Integer.class);
		input.setManualValue(-1);
		addInput(input);
		rand = new Random();
		addOutput(new Output("res", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int seed = getInput("seed").getData(Integer.class);
		Random randUsed = seed < 0 ? rand : new Random(getInput("seed").getData(Integer.class));
		
		Double val = randUsed.nextDouble();
		getOutput("res").setData(val);
	}

}
