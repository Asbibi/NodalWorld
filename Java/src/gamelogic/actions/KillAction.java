package gamelogic.actions;

import gamelogic.Action;
import gamelogic.Entity;
import gamelogic.Species;

public class KillAction implements Action {

	private Entity entity;

	public KillAction(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void execute() {
		Species sp = entity.getSpecies();
		sp.removeMember(entity);
	}

}
