package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.nodes.GenerateNode;

import java.util.Collection;
import java.util.LinkedList;

/**
* Rule used to generate new members in a species.
* 
* @see GenerateNode
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
	public void apply(GameManager game) {
		network.evaluate(game);
		
		if(terminalNode.generate()) {
			game.getCurrentSpecies().addMemberAt(terminalNode.position(), game.getFrame());
		}
	}

}
