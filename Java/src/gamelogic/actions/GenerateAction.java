package gamelogic.actions;

import gamelogic.Action;
import gamelogic.Species;
import gamelogic.Vec2D;

public class GenerateAction implements Action {

	private Species sp;
	private Vec2D pos;
	private Integer birthTime;

	public GenerateAction(Species sp, Vec2D pos, Integer birthTime) {
		this.sp = sp;
		this.pos = pos;
		this.birthTime = birthTime;
	}

	@Override
	public void execute() {
		sp.addMemberAt(pos, birthTime);
	}

}
