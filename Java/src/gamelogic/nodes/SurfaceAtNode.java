package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Surface;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.Output;

/**
* The node model used to get the surface in the tile at a given position.
* 
* @see GameManager
*/
public class SurfaceAtNode extends Node {

	/**
	*
	*/ 
	public SurfaceAtNode() {
		super();
		addInput(new Input("position", Vec2D.class));
		addOutput(new Output("surface", Surface.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Vec2D pos = getInput("position").getData(Vec2D.class);
		getOutput("surface").setData(game.surfaceAt(pos));
	}

}
