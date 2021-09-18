package gamelogic.actions;

import gamelogic.Action;
import gamelogic.Species;
import gamelogic.Vec2D;

/**
* Action used to generate new members in a species.
* 
* @see Species
*/ 
public class GenerateAction implements Action {

	private Species sp;
	private Vec2D pos;
	private Integer birthTime;

	/**
	* @param sp
	* @param pos
	* @param birthTime
	*/ 
	public GenerateAction(Species sp, Vec2D pos, Integer birthTime) {
		this.sp = sp;
		this.pos = pos;
		this.birthTime = birthTime;
	}

	/**
	* Creates a new member in the species at the given position and birth time.
	*/ 
	@Override
	public void execute() {
		sp.addMemberAt(pos, birthTime);
	}

}
