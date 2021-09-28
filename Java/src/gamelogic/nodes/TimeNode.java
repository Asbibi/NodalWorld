package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;

/**
* The node model used to get the current frame. <br/>
* 
* Inputs : None <br/>
* Outputs : time
* 
* @see GameManager
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
	public void evaluate(GameManager game) {
		getOutput("time").setData(game.getFrame());
	}

}
