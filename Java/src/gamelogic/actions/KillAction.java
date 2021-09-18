package gamelogic.actions;

import gamelogic.Action;
import gamelogic.Entity;
import gamelogic.Species;

/**
* Action used to kill an entity, meaning removing ot from its species.
* 
* @see Entity
* @see Species
*/ 
public class KillAction implements Action {

	private Entity entity;

	/**
	* @param entity
	*/ 
	public KillAction(Entity entity) {
		this.entity = entity;
	}

	/**
	* Removes the given entity from its species.
	*/ 
	@Override
	public void execute() {
		Species sp = entity.getSpecies();
		sp.removeMember(entity);
	}

}
