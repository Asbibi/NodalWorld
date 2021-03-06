package gamelogic.nodes;

import gamelogic.Entity;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The node model used to detect if an entity is in a rectangular area. <br/>
* 
* Inputs : entity, position, dimension <br/>
* Outputs : inside
* 
*/
public class EntityInAreaNode  extends Node {

	/**
	*
	*/ 
	public EntityInAreaNode() {
		super("Entity In Area");
		addInput(new Input("entity", Entity.class));
		addInput(new Input("position", Vec2D.class));
		addInput(new Input("dimension", Vec2D.class));
		addOutput(new Output("inside", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Entity entity = getInput("entity").getData(Entity.class);
		boolean isInside = entity.isInArea(getInput("position").getData(Vec2D.class), getInput("dimension").getData(Vec2D.class));
		if (!isInside) {
			getOutput("inside").setData(false);
			return;
		}
		getOutput("inside").setData(isInside);
	}

}
