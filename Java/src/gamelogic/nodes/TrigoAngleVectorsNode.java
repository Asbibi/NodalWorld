package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The node model used to compute the angle between two vectors. <br/>
* 
* Inputs : vec1, vec2 <br/>
* Outputs : angle
* 
*/
public class TrigoAngleVectorsNode extends Node {


	/**
	* 
	*/ 
	public TrigoAngleVectorsNode() {
		super("Vectors Angle");
		addInput(new Input("vec1", Vec2D.class));
		addInput(new Input("vec2", Vec2D.class));
		addOutput(new Output("angle", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {		
		getOutput("angle").setData(Vec2D.angleBetween(getInput("vec1").getData(Vec2D.class), getInput("vec2").getData(Vec2D.class)));
	}

}
