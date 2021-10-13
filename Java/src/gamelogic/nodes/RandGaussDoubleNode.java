package gamelogic.nodes;

import java.util.Random;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class RandGaussDoubleNode extends Node {

	private Random rand;

	/**
	* 
	*/ 
	public RandGaussDoubleNode() {
		super("Gaussian");
		Input inputSeed = new Input("seed", Integer.class);
		inputSeed.setManualValue(-1);
		addInput(inputSeed);
		rand = new Random();
		addInput(new Input("mean", Double.class));
		Input input = new Input("deviation", Double.class);
		input.setManualValue(1.);
		addInput(input);
		addOutput(new Output("res", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int seed = getInput("seed").getData(Integer.class);
		Random randUsed = seed < 0 ? rand : new Random(getInput("seed").getData(Integer.class));
		
		Double mean = getInput("Mean").getData(Double.class);
		Double devia = getInput("Deviation").getData(Double.class);
		Double val = randUsed.nextGaussian() * devia + mean;
		getOutput("res").setData(val);
	}

}
