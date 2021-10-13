package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class OneMinusNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public OneMinusNode() {
		super("One minus double");
		addInput(new Input("x", Double.class));
		addOutput(new Output("1-x", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("1-x").setData(1 - getInput("x").getData(Double.class));
	}


}
