package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class ConvertIntDoubleNode extends Node {

	/**
	* 
	*/ 
	public ConvertIntDoubleNode() {
		super("Int to Double");
		addInput(new Input("val", Integer.class));
		addOutput(new Output("val", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("val").setData(getInput("val").getData(Integer.class).doubleValue());
	}
}