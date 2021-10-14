package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerminalNode;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.rules.DeathRule;

/**
* The node model used as terminal node in a death rule's network. <br/>
* 
* Inputs : kill <br/>
* Outputs :
* 
* @see DeathRule
*/ 
public class KillNode extends TerminalNode<DeathRule> {

	private boolean kill;

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
	public void evaluate(GameManager game) throws NetworkIOException {
		kill = getInput("kill").getData(Boolean.class);
	}

	/**
	* @return the data retrieved in the "kill" input, used to decide if an entity should be removed or not
	*/ 
	public boolean kill() {
		return kill;
	}

}
