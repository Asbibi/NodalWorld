package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class DivIntNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public DivIntNode() {
		super("Divide : Int");
		addInput(new Input("a", Integer.class));
		Input input = new Input("b", Integer.class);
		input.setManualValue(1);
		addInput(input);		
		addOutput(new Output("a/b", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		double b = getInput("b").getData(Integer.class);
		if (b == 0) {
			getOutput("a/b").setData(null);
			return;
		}
		getOutput("a/b").setData((getInput("a").getData(Integer.class)).doubleValue() / b);
	}


}
