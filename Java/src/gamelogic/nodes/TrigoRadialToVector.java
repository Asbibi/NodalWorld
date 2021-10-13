package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public class TrigoRadialToVector  extends Node {

	/**
	*
	*/ 
	public TrigoRadialToVector() {
		super("Vector from Radial");
		addInput(new Input("angle", Double.class));
		addInput(new Input("norm", Double.class));
		addOutput(new Output("vec", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		double angle = getInput("angle").getData(Double.class);
		double norm = getInput("norm").getData(Double.class);
		int x = (int)(Math.cos(angle) * norm);
		int y = (int)(Math.sin(angle) * norm);
		getOutput("vec").setData(new Vec2D(x, y));
	}
}
