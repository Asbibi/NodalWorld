package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerminalNode;
import gamelogic.Input;
import gamelogic.rules.DeathRule;

/**
* The node model used as terminal node in a death rule's network. <br/>
* 
* Inputs : kill <br/>
* Outputs : none
* 
* @see DeathRule
*/ 
public class KillNode extends TerminalNode<DeathRule> {

	/**
	* 
	*/ 
	public KillNode() {
		super("Death");
		addInput(new Input("kill", Boolean.class));
	}

	@Override
	protected void initRule() {
		rule = new DeathRule(this);
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
