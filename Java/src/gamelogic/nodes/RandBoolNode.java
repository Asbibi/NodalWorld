package gamelogic.nodes;

import java.util.Random;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class RandBoolNode extends Node {

	private Random rand;

	/**
	* 
	*/ 
	public RandBoolNode() {
		super("Random Boolean");
		Input input = new Input("seed", Integer.class);
		input.setManualValue(-1);
		addInput(input);
		rand = new Random();
		addInput(new Input("proba", Double.class));
		addOutput(new Output("res", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int seed = getInput("seed").getData(Integer.class);
		Random randUsed = seed < 0 ? rand : new Random(getInput("seed").getData(Integer.class));
		
		Double p = getInput("proba").getData(Double.class);		
		Double val = randUsed.nextDouble();
		getOutput("res").setData(val < p);
	}

}
