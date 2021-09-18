package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Vec2D;

public class GenerateNode extends Node {

	public GenerateNode() {
		super();
		addInput(new Input("generate", Boolean.class));
		addInput(new Input("position", Vec2D.class));
	}

	@Override
	public void evaluate(GameManager game) {
		return;
	}

	public boolean generate() {
		Boolean result = getInput("generate").getData(Boolean.class);
		if(result== null) return false;
		else return result;
	}

	public Vec2D position() {
		return getInput("position").getData(Vec2D.class);
	}

}
