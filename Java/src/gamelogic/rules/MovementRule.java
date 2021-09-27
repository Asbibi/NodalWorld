package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.Vec2D;
import gamelogic.nodes.MoveNode;

import java.util.Collection;
import java.util.LinkedList;

/**
* Rule used to move entities.
* 
* @see MoveNode
*/ 
public class MovementRule extends Rule {

	private MoveNode terminalNode;

	/**
	*
	*/ 
	public MovementRule() {
		super();
		terminalNode = new MoveNode();
		network.addNode(terminalNode);
	}

	/**
	* @return the terminal node of the network defining the rule
	*/ 
	public MoveNode getTerminalNode() {
		return terminalNode;
	}

	/**
	* @param game
	*/ 
	@Override
	public void apply(GameManager game) {
		network.evaluate(game);

		Vec2D nextPos = terminalNode.position();
		if(nextPos!=null) {
			game.getCurrentEntity().setPos(nextPos);
		}
	}

}
