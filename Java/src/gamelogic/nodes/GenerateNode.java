package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Vec2D;

/**
* The node model used as terminal node in a generation rule's network.
* 
* @see GenerationRule
*/ 
public class GenerateNode extends Node {

	/**
	*
	*/ 
	public GenerateNode() {
		super();
		addInput(new Input("generate", Boolean.class));
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
	* @return the data retrieved in the "generate" input, used to decide if a new entity should be created or not
	*/ 
	public boolean generate() {
		Boolean result = getInput("generate").getData(Boolean.class);
		if(result== null) return false;
		else return result;
	}

	/**
	* return the data retrieved in the "position" input, used to decide where a newly created entity should be placed
	*/ 
	public Vec2D position() {
		return getInput("position").getData(Vec2D.class);
	}

}
