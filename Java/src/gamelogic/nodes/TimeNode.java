package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;
import gamelogic.NetworkIOException;

/**
* The node model used to get the current frame. <br/>
* 
* Inputs : <br/>
* Outputs : time
* 
*/
public class TimeNode extends Node {

	/**
	*
	*/ 
	public TimeNode() {
		super("Time");
		addOutput(new Output("time", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("time").setData(game.getFrame());
	}

}
