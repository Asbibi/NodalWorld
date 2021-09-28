package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;

/**
* The node model used to add two doubles.
* 
* @see AddNode
* @see GameManager
*/
public class DoubleAddNode extends AddNode<Double> {

	/**
	* 
	*/ 
	public DoubleAddNode() {
		super("Double Add", Double.class);
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	Double add(Double val1, Double val2) {
		return val1+val2;
	} 

}
