package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* The node model used to replace a double with the closest integer. <br/>
* 
* Inputs : val <br/>
* Outputs : val
* 
*/
public class ConvertDoubleIntApproxNode extends Node {

	/**
	* 
	*/ 
	public ConvertDoubleIntApproxNode() {
		super("Double ~ Int");
		addInput(new Input("val", Double.class));
		addOutput(new Output("val", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Double inputVal = getInput("val").getData(Double.class);
		int intVal = (int)inputVal.doubleValue();
		if (inputVal - intVal > 0.5)
			intVal++;
		getOutput("val").setData(intVal);		
	}
}
