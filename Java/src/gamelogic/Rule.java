package gamelogic;

import java.lang.Object;

import java.util.Collection;

/**
* Abstract model for the rules defined by the user through a network of operators.
* Classes that extend this class will usually rely on a specific node in the network - called a terminal node - for the implementation of the apply method.
* 
* @see Network
*/ 
public abstract class Rule {

	private static int idCounter = 0;

	private int id;
	protected Network network;

	/**
	*
	*/ 
	public Rule() {
		this.id = idCounter;
		idCounter++;
		network = new Network();
	}

	/**
	* @return the rule's network
	*/ 
	public Network getNetwork() {
		return network;
	}

	/**
	* Checks if two rules are equal using their ids.
	*/ 
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Rule)) return false;

		Rule rule = (Rule) o;
		return (id == rule.id);
	}

	/**
	* @param game
	*/ 
	public abstract void apply(GameManager game);

}
