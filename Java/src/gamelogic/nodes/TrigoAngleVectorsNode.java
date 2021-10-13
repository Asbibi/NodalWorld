package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public class TrigoAngleVectorsNode extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public TrigoAngleVectorsNode() {
		super("Vectors Angle");
		addInput(new Input("vect1", Vec2D.class));
		addInput(new Input("vect2", Vec2D.class));
		addOutput(new Output("angle", Double.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {		
		getOutput("angle").setData(Vec2D.angleBetween(getInput("vect1").getData(Vec2D.class), getInput("vect2").getData(Vec2D.class)));
	}

}
