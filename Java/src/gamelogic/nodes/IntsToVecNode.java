package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.Output;

/**
* The node model used to gather two integers to form a 2D vector. <br/>
* 
* Inputs : x, y <br/>
* Outputs : vec
* 
* @see GameManager
*/
public class IntsToVecNode extends Node {

	/**
	*
	*/ 
	public IntsToVecNode() {
		super("Gather");
		addInput(new Input("x", Integer.class));
		addInput(new Input("y", Integer.class));
		addOutput(new Output("vec", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Integer x = getInput("x").getData(Integer.class);
		Integer y = getInput("y").getData(Integer.class);
		getOutput("vec").setData(new Vec2D(x, y));
	}

}
