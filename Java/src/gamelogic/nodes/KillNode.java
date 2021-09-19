package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;

/**
* The node model used as terminal node in a death rule's network. <br/>
* 
* Inputs : kill <br/>
* Outputs : none
* 
* @see DeathRule
*/ 
public class KillNode extends Node {

	/**
	*
	*/ 
	public KillNode() {
		super();
		addInput(new Input("kill", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		return;
	}

	/**
	* @return the data retrieved in the "kill" input, used to decide if an entity should be removed or not
	*/ 
	public boolean kill() {
		Boolean result = getInput("kill").getData(Boolean.class);
		if(result==null) return false;
		else return result;
	}

}
