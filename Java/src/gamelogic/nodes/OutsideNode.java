package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Vec2D;
import gamelogic.NetworkIOException;

/**
* The node model used to know if a given position is inside the terrain or not. <br/>
* 
* Inputs : pos <br/>
* Outputs : is out
* 
*/
public class OutsideNode extends Node {

	/**
	*
	*/ 
	public OutsideNode() {
		super("Is Outside World");
		addInput(new Input("pos", Vec2D.class));
		addOutput(new Output("is out", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D pos = getInput("pos").getData(Vec2D.class);
		getOutput("is out").setData(pos.getX() < 0 || pos.getX() >= game.gridWidth() || pos.getY() < 0 || pos.getY() >= game.gridHeight());
	}

}
