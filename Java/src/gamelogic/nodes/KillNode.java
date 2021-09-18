package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;

public class KillNode extends Node {

	public KillNode() {
		super();
		addInput(new Input("kill", Boolean.class));
	}

	@Override
	public void evaluate(GameManager game) {
		return;
	}

	public boolean kill() {
		Boolean result = getInput("kill").getData(Boolean.class);
		if(result==null) return false;
		else return result;
	}

}
