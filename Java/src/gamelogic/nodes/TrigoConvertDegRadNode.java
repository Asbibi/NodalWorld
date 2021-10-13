package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class TrigoConvertDegRadNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public TrigoConvertDegRadNode() {
		super("Deg to Rad");
		addInput(new Input("deg", Integer.class));	
		addOutput(new Output("rad", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int deg = getInput("deg").getData(Integer.class);
		double rad = (deg*Math.PI) /180;
		getOutput("rad").setData(rad);
	}

}
