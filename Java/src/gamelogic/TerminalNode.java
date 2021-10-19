package gamelogic;

/**
* Abstract class for terminal nodes which are connected to rules.
* 
* @see Node
* @see Rule
*/ 
public abstract class TerminalNode<R extends Rule> extends Node {

	private static final long serialVersionUID = 8094688124751769422L;
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
