package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class DivDoubleNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public DivDoubleNode() {
		super("Divide : Double");
		addInput(new Input("a", Double.class));
		Input input = new Input("b", Double.class);
		input.setManualValue(1.);
		addInput(input);		
		addOutput(new Output("a/b", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		double b = getInput("b").getData(Double.class);
		if (b == 0) {
			getOutput("a/b").setData(null);
			return;
		}
		getOutput("res").setData(getInput("a").getData(Double.class) / b);
	}


}
