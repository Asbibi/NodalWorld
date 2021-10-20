package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

/**
* This node allows to multiply int with double. <br/>
* Its int output is an approximation for the exact result that is available on the double output.<br/>
* 
* Inputs : a, b <br/>
* Outputs : a*b, ~a*b
* 
*/
public class MultiplyIntDoubleNode extends Node {

	public MultiplyIntDoubleNode() {
		super("Multiply : Int-Double");
		addInput(new Input("a", Integer.class));
		addInput(new Input("b", Double.class));
		addOutput(new Output("~a*b", Integer.class));
		addOutput(new Output("a*b", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int a = getInput("a").getData(Integer.class);
		double b = getInput("b").getData(Double.class);
		Double ab = a * b;
		getOutput("a*b").setData(ab);
		int ab_ = (int)ab.doubleValue();
		if (ab - ab_ > 0.5)
			ab_++;
		getOutput("~a*b").setData(ab_);
	}	
}
