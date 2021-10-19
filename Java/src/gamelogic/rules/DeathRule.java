package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.nodes.KillNode;

/**
* Rule used to kill entities.
* 
* @see KillNode
*/ 
public class DeathRule extends Rule<KillNode> {

	private static final long serialVersionUID = 1620143180129461019L;

	/**
	* @param terminalNode
	*/ 
	public DeathRule(KillNode terminalNode) {
		super(terminalNode);
	}

	/**
	* Read the terminal node to decide if the currently processed entity should be killed.
	* 
	* @param game
	*/ 
	@Override
	public void apply(GameManager game) {
		if(terminalNode.kill()) {
			game.addDeadEntity(game.getCurrentEntity());
		}
	}

}
