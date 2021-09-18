package gamelogic.rules;

import gamelogic.GameManager;
import gamelogic.Rule;
import gamelogic.Action;
import gamelogic.nodes.KillNode;
import gamelogic.actions.KillAction;

import java.util.Collection;
import java.util.LinkedList;

public class DeathRule extends Rule {

	private KillNode terminalNode;

	public DeathRule() {
		super();
		terminalNode = new KillNode();
		network.addNode(terminalNode);
	}

	@Override
	public Collection<Action> apply(GameManager game) {
		network.evaluate(game);

		Collection<Action> actions = new LinkedList<Action>();
		if(terminalNode.kill()) {
			actions.add(new KillAction(game.getCurrentEntity()));
		}
		return actions;
	}

}
