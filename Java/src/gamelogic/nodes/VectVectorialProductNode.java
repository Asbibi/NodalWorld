package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public class VectVectorialProductNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public VectVectorialProductNode() {
		super("Vectorial Product");
		addInput(new Input("val1", Vec2D.class));
		addInput(new Input("val2", Vec2D.class));
		addOutput(new Output("res", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {		
		getOutput("res").setData(Vec2D.vectorialProduct(getInput("val1").getData(Vec2D.class), getInput("val2").getData(Vec2D.class)));
	}

}
