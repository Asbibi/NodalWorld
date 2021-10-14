package gamelogic;

import java.io.Serializable;
import java.lang.Object;

import java.util.Collection;

/**
* Abstract model for the rules defined by the user through a network of operators.
* Rules rely on a specific node in the network - called a terminal node - for the implementation of the apply method.
* 
* @see Network
* @see TerminalNode
*/ 
public abstract class Rule<T extends TerminalNode> implements Serializable {

	private static int idCounter = 0;

	private int id;
	protected T terminalNode;

	/**
	* @param terminalNode
	*/ 
	public Rule(T terminalNode) {
		this.id = idCounter;
		idCounter++;
		this.terminalNode = terminalNode;
	}

	/**
	* @return the rule's terminal node
	*/ 
	public T getTerminalNode() {
		return terminalNode;
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
	* Once the network corresponding to the terminal node has been evaluated, retrieve data from that node and apply actions accordingly.
	* These actions will depend on the specific rule subclass.
	* 
	* @param game
	*/ 
	public abstract void apply(GameManager game);

}
