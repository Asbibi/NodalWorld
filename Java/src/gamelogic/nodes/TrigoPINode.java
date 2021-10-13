package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class TrigoPINode extends Node {

	/**
	* @param name 
	* @param dataClass the class object representing the type of the objects to test
	*/ 
	public TrigoPINode() {
		super("PI");
		addOutput(new Output("pi", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("pi").setData(Math.PI);
	}

}
