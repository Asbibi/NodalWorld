package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class TrigoCosNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public TrigoCosNode() {
		super("Cosinus");
		addInput(new Input("t", Double.class));	
		addOutput(new Output("cos(t)", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("cos(t)").setData(Math.cos(getInput("t").getData(Double.class)));
	}

}
