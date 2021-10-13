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
		rand = new Random();
		addInput(new Input("Mean", Double.class));
		Input input = new Input("Deviation", Double.class);
		input.setManualValue((Double)1.);
		addInput(input);
		addOutput(new Output("val", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Double mean = getInput("Mean").getData(Double.class);
		Double devia = getInput("Deviation").getData(Double.class);
		Double val = rand.nextGaussian() * devia + mean;
		getOutput("val").setData(val);
		
	}

}
