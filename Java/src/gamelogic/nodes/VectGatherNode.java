package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to gather two integers to form a 2D vector. <br/>
* 
* Inputs : x, y <br/>
* Outputs : vec
* 
*/
public class VectGatherNode extends Node {

	/**
	*
	*/ 
	public VectGatherNode() {
		super("Variable : Vector");
		addInput(new Input("x", Integer.class));
		addInput(new Input("y", Integer.class));
		addOutput(new Output("vec", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Integer x = getInput("x").getData(Integer.class);
		Integer y = getInput("y").getData(Integer.class);
		getOutput("vec").setData(new Vec2D(x, y));
	}

}
