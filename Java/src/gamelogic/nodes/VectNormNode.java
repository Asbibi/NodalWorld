package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Output;
import gamelogic.Vec2D;

public class VectNormNode extends Node{

	public VectNormNode(){
		super("Norm");
		addInput(new Input("vec", Vec2D.class));
		addOutput(new Output("norm", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D vec = getInput("vec").getData(Vec2D.class);
		getOutput("norm").setData(vec.getNorm());
	}
}
