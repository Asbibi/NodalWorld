package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.Action;
import gamelogic.nodes.KillNode;
import gamelogic.actions.KillAction;

import java.util.Collection;
import java.util.LinkedList;

/**
* Rule used to kill entities.
* 
* @see KillNode
* @see KillAction
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
	public Collection<Action> apply(GameManager game) {
		network.evaluate(game);

		Collection<Action> actions = new LinkedList<Action>();
		if(terminalNode.kill()) {
			actions.add(new KillAction(game.getCurrentEntity()));
		}
		return actions;
	}

}
