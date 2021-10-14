package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to convert degrees to radians. <br/>
* 
* Inputs : deg <br/>
* Outputs : rad
* 
*/
public class TrigoConvertDegRadNode extends Node {

	/**
	* 
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
