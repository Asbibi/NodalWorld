package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Vec2D;

public class MoveNode extends Node {

	public MoveNode() {
		super();
		addInput(new Input("position", Vec2D.class));
	}

	@Override
	public void evaluate(GameManager game) {
		return;
	}

	public Vec2D position() {
		return getInput("position").getData(Vec2D.class);
	}

}
