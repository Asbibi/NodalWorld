package gamelogic;

import java.io.Serializable;

/**
* This class provides the abstract edge model for the edges used in the nodal system.
* 
* @see Node
*/ 
public class Edge implements Serializable {

	private static final long serialVersionUID = 797864409022863271L;
	private Node source, target;

	/**
	* @param source
	* @param target
	*/ 
	public Edge(Node source, Node target) {
		this.source = source;
		this.target = target;
	}

	/**
	* @return the source node of the edge
	*/ 
	public Node getSource() {
		return source;
	}

	/**
	* @return the target node of the edge
	*/
	public Node getTarget() {
		return target;
	}

}
