package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.Action;
import gamelogic.Vec2D;
import gamelogic.nodes.MoveNode;
import gamelogic.actions.MoveAction;

import java.util.Collection;
import java.util.LinkedList;

/**
* Rule used to move entities.
* 
* @see MoveNode
* @see MoveAction
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
	* @param game
	*/ 
	@Override
	public Collection<Action> apply(GameManager game) {
		network.evaluate(game);

		Collection<Action> actions = new LinkedList<Action>();
		Vec2D nextPos = terminalNode.position();
		if(nextPos==null) return actions;
		actions.add(new MoveAction(game.getCurrentEntity(), nextPos));
		return actions;
	}

}
