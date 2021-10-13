package gamelogic.nodes;

import gamelogic.Entity;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public class EntityInAreaNode  extends Node {


	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public EntityInAreaNode() {
		super("Entity In Area");
		addInput(new Input("entity", Entity.class));
		addInput(new Input("position", Vec2D.class));
		addInput(new Input("dimension", Vec2D.class));
		addOutput(new Output("inside", Boolean.class));
		addOutput(new Output("position", Vec2D.class));
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
			getOutput("position").setData(new Vec2D(-1,-1));
			return;
		}
		getOutput("inside").setData(isInside);
		getOutput("position").setData(entity.getPos());
	}

}
