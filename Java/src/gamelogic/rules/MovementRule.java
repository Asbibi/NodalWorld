package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.Vec2D;
import gamelogic.nodes.MoveNode;

/**
* Rule used to move entities.
* 
* @see MoveNode
*/ 
public class MovementRule extends Rule<MoveNode> {
	
	private static final long serialVersionUID = -1679464283527829799L;

	/**
	* @param terminalNode
	*/ 
	public MovementRule(MoveNode terminalNode) {
		super(terminalNode);
	}

	/**
	* Read the terminal node to decide where to move the currently processed entity.
	* 
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
