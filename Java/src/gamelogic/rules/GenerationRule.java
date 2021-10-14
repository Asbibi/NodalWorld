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
public class GenerationRule extends Rule<GenerateNode> {

	/**
	* @param terminalNode
	*/ 
	public GenerationRule(GenerateNode terminalNode) {
		super(terminalNode);
	}

	/**
	* Read the terminal node to decide if a new member should be created in the currently processed species.
	* 
	* @param game
	*/ 
	@Override
	public void apply(GameManager game) {
		if(terminalNode.generate()) {
			game.getCurrentSpecies().addMemberAt(terminalNode.position(), game.getFrame());
		}
	}

}
