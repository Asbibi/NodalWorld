package gamelogic;

/**
* Abstract class for terminal nodes which are connected to rules.
* 
* @see Node
* @see Rule
*/ 
public abstract class TerminalNode<R extends Rule> extends Node {

	protected R rule;

	/**
	* @param name
	*/ 
	public TerminalNode(String name) {
		super(name);
		initRule();
	}

	protected abstract void initRule();

	/**
	* @return the rule connected to this terminal node
	*/ 
	public R getRule() {
		return rule;
	}

}
