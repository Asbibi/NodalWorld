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
public class DeathRule extends Rule {

	private KillNode terminalNode;

	/**
	*
	*/ 
	public DeathRule() {
		super();
		terminalNode = new KillNode();
		network.addNode(terminalNode);
	}

	/**
	* @return the terminal node of the network defining the rule
	*/ 
	public KillNode getTerminalNode() {
		return terminalNode;
	}

	/**
	* @param game
	*/ 
	@Override
	public void apply(GameManager game) {
		network.evaluate(game);

		if(terminalNode.kill()) {
			game.getCurrentEntity().getSpecies().removeMember(game.getCurrentEntity());
		}
	}

}
