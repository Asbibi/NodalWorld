package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to convert radians to degrees. <br/>
* 
* Inputs : rad <br/>
* Outputs : deg
* 
*/
public class TrigoConvertRadDegNode extends Node {


	/**
	* 
	*/ 
	public TrigoConvertRadDegNode() {
		super("Rad to Deg");
		addInput(new Input("rad", Double.class));	
		addOutput(new Output("deg", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		double rad = getInput("rad").getData(Double.class);
		int deg = (int)(rad*180 / Math.PI);
		getOutput("deg").setData(deg);
	}
}
