package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class TrigoSinNode  extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public TrigoSinNode() {
		super("Sinus");
		addInput(new Input("t (rad)", Double.class));	
		addOutput(new Output("sin(t)", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("sin(t)").setData(Math.sin(getInput("t (rad)").getData(Double.class)));
	}

}
