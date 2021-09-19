package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;

/**
* The node model used to substract two doubles.
* 
* @see SubNode
* @see GameManager
*/
public class DoubleSubNode extends SubNode<Double> {

	/**
	* 
	*/ 
	public DoubleSubNode() {
		super(Double.class);
	}

	/**
	* @param val1
	* @param val2
	* @return the difference between val1 and val2
	*/ 
	Double sub(Double val1, Double val2) {
		return val1-val2;
	} 

}
