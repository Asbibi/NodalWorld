package gamelogic;

/**
* Abstract class for terminal nodes, connected to a rule
* 
* @see Node
* @see Rule
*/ 
public abstract class TerminalNode<R extends Rule> extends Node {

	protected R rule;

	/**
	* 
	*/ 
	public TerminalNode() {
		super();
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
