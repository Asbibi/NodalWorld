package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The node model used to calculate the vectorial product of two vectors. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
*/
public class VectVectorialProductNode extends Node {


	/**
	* 
	*/ 
	public VectVectorialProductNode() {
		super("Vectorial Product");
		addInput(new Input("val1", Vec2D.class));
		addInput(new Input("val2", Vec2D.class));
		addOutput(new Output("res", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {		
		getOutput("res").setData(Vec2D.vectorialProduct(getInput("val1").getData(Vec2D.class), getInput("val2").getData(Vec2D.class)));
	}

}
