package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.Output;

/**
* The node model used to split a 2D vector into two integers (its components). <br/>
* 
* Inputs : vec <br/>
* Outputs : x, y
* 
* @see GameManager
*/
public class VecToIntsNode extends Node {

	/**
	*
	*/ 
	public VecToIntsNode() {
		super();
		addInput(new Output("vec", Vec2D.class));
		addOutput(new Input("x", Integer.class));
		addOutput(new Input("y", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Vec2D vec = getInput("vec").getData(Vec2D.class);
		getOutput("x").setData(vec.getX());
		getOutput("y").setData(vec.getY());
	}

}