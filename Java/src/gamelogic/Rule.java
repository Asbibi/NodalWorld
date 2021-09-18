package gamelogic;

import java.lang.Object;

import java.util.Collection;

/**
* @see GameManager
* @see Action
*/ 
public abstract class Rule {

	private static int idCounter = 0;

	private int id;
	protected Network network;

	public Rule() {
		this.id = idCounter;
		idCounter++;
		network = new Network();
	}

	public Network getNetwork() {
		return network;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Rule)) return false;

		Rule rule = (Rule) o;
		return (id == rule.id);
	}

	public abstract Collection<Action> apply(GameManager game);

}
