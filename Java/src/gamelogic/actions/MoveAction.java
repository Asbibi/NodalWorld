package gamelogic.actions;

import gamelogic.Action;
import gamelogic.Entity;
import gamelogic.Vec2D;

public class MoveAction implements Action {

	private Entity entity;
	private Vec2D pos;

	public MoveAction(Entity entity, Vec2D pos) {
		this.entity = entity;
		this.pos = pos;
	}

	@Override
	public void execute() {
		entity.setPos(pos);
	}

}
