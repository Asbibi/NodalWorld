package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to split a 2D vector into two integers (its components). <br/>
* 
* Inputs : vec <br/>
* Outputs : x, y
* 
*/
public class VectSplitNode extends Node {

	/**
	*
	*/ 
	public VectSplitNode() {
		super("Split");
		addInput(new Input("vec", Vec2D.class));
		addOutput(new Output("x", Integer.class));
		addOutput(new Output("y", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D vec = getInput("vec").getData(Vec2D.class);
		getOutput("x").setData(vec.getX());
		getOutput("y").setData(vec.getY());
	}

}
