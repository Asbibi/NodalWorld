package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.nodes.KillNode;

import java.util.Collection;
import java.util.LinkedList;

/**
* Rule used to kill entities.
* 
* @see KillNode
*/ 
public class DeathRule extends Rule<KillNode> {

	/**
	* @param terminalNode
	*/ 
	public DeathRule(KillNode terminalNode) {
		super(terminalNode);
	}

	/**
	* @param game
	*/ 
	@Override
	public void apply(GameManager game) {
		if(terminalNode.kill()) {
			game.getCurrentEntity().getSpecies().removeMember(game.getCurrentEntity());
		}
	}

}
