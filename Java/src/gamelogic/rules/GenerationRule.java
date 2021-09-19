package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.Action;
import gamelogic.nodes.GenerateNode;
import gamelogic.actions.GenerateAction;

import java.util.Collection;
import java.util.LinkedList;

/**
* Rule used to generate new members in a species.
* 
* @see GenerateNode
* @see GenerateAction
*/ 
public class GenerationRule extends Rule {

	private GenerateNode terminalNode;

	/**
	*
	*/ 
	public GenerationRule() {
		super();
		terminalNode = new GenerateNode();
		network.addNode(terminalNode);
	}

	/**
	* @return the terminal node of the network defining the rule
	*/ 
	public GenerateNode getTerminalNode() {
		return terminalNode;
	}

	/**
	* @param game
	*/ 
	@Override
	public Collection<Action> apply(GameManager game) {
		network.evaluate(game);
		
		Collection<Action> actions = new LinkedList<Action>();
		if(terminalNode.generate()) {
			actions.add(new GenerateAction(game.getCurrentSpecies(), terminalNode.position(), game.getFrame()));
		}
		return actions;
	}

}
