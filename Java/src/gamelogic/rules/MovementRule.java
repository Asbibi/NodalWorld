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
public class MovementRule extends Rule<MoveNode> {

	/**
	* @param terminalNode
	*/ 
	public MovementRule(MoveNode terminalNode) {
		super(terminalNode);
	}

	/**
	* @param game
	*/ 
	@Override
	public void apply(GameManager game) {
		Vec2D nextPos = terminalNode.position();
		if(nextPos!=null) {
			game.getCurrentEntity().setPos(nextPos);
		}
	}

}
