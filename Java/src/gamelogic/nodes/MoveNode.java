package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerminalNode;
import gamelogic.Input;
import gamelogic.Vec2D;
import gamelogic.rules.MovementRule;

/**
* The node model used as terminal node in a movement rule's network. <br/>
* 
* Inputs : position <br/>
* Outputs : none
* 
* @see MovementRule
*/ 
public class MoveNode extends TerminalNode<MovementRule> {

	/**
	*
	*/ 
	public MoveNode() {
		super("Movement");
		addInput(new Input("position", Vec2D.class));
	}

	@Override
	protected void initRule() {
		rule = new MovementRule(this);
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		return;
	}

	/**
	* return the data retrieved in the "position" input, used to decide where an entity should move
	*/ 
	public Vec2D position() {
		return getInput("position").getData(Vec2D.class);
	}

}
