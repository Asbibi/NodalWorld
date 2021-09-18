package gamelogic.actions;

import gamelogic.Action;
import gamelogic.Entity;
import gamelogic.Vec2D;

/**
* Action used to move entities.
* 
* @see Entity
*/ 
public class MoveAction implements Action {

	private Entity entity;
	private Vec2D pos;

	/**
	* @param entity
	* @param pos
	*/ 
	public MoveAction(Entity entity, Vec2D pos) {
		this.entity = entity;
		this.pos = pos;
	}

	/**
	* Moves the entity to the given position.
	*/ 
	@Override
	public void execute() {
		entity.setPos(pos);
	}

}
