package gamelogic.nodes;

import java.util.Random;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class ConvertDoubleIntNode extends Node {

	/**
	* 
	*/ 
	public ConvertDoubleIntNode() {
		super("Double to Int");
		addInput(new Input("val", Double.class));
		addOutput(new Output("val", Integer.class));
		addOutput(new Output("rest", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Double inputVal = getInput("val").getData(Double.class);
		int intVal = (int)inputVal.doubleValue();
		getOutput("val").setData(intVal);
		getOutput("rest").setData(inputVal - intVal);		
	}
}
