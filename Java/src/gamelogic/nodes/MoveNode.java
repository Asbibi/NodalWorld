package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Vec2D;

/**
* The node model used as terminal node in a movement rule's network. <br/>
* 
* Inputs : position <br/>
* Outputs : none
* 
* @see MovementRule
*/ 
public class MoveNode extends Node {

	/**
	*
	*/ 
	public MoveNode() {
		super();
		addInput(new Input("position", Vec2D.class));
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
