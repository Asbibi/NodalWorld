package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Surface;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to get the surface in the tile at a given position. <br/>
* 
* Inputs : position <br/>
* Outputs : surface
* 
*/
public class SurfaceAtNode extends Node {

	/**
	*
	*/ 
	public SurfaceAtNode() {
		super("Surface At");
		addInput(new Input("position", Vec2D.class));
		addOutput(new Output("surface", Surface.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D pos = getInput("position").getData(Vec2D.class);
		getOutput("surface").setData(game.surfaceAt(pos));
	}

}
