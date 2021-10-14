package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerminalNode;
import gamelogic.Input;
import gamelogic.Vec2D;
import gamelogic.NetworkIOException;
import gamelogic.rules.GenerationRule;

/**
* The node model used as terminal node in a generation rule's network. <br/>
* 
* Inputs : generate, position <br/>
* Outputs :
* 
* @see GenerationRule
*/ 
public class GenerateNode extends TerminalNode<GenerationRule> {

	private boolean generate;
	private Vec2D position;

	/**
	* 
	*/ 
	public GenerateNode() {
		super("Generation");
		addInput(new Input("generate", Boolean.class));
		addInput(new Input("position", Vec2D.class));
	}

	@Override
	protected void initRule() {
		rule = new GenerationRule(this);
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		generate = getInput("generate").getData(Boolean.class);
		position = getInput("position").getData(Vec2D.class);
	}

	/**
	* @return the data retrieved in the "generate" input, used to decide if a new entity should be created or not
	*/ 
	public boolean generate() {
		return generate;
	}

	/**
	* return the data retrieved in the "position" input, used to decide where a newly created entity should be placed
	*/ 
	public Vec2D position() {
		return position;
	}

}
