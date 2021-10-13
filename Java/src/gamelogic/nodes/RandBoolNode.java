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
		rand = new Random();
		addInput(new Input("proba", Double.class));
		addOutput(new Output("val", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Double p = getInput("proba").getData(Double.class);
		if ( p < 0 || 1 < p) {
			getOutput("val").setData(null);
			return;
		}
		Double val = rand.nextDouble();
		getOutput("val").setData(val < p);
	}

}
