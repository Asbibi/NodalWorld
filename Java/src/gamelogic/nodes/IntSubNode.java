package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;

/**
* The node model used to substract two integers.
* 
* @see SubNode
* @see GameManager
*/
public class IntSubNode extends SubNode<Integer> {

	/**
	* 
	*/ 
	public IntSubNode() {
		super(Integer.class);
	}

	/**
	* @param val1
	* @param val2
	* @return the difference between val1 and val2
	*/ 
	Integer sub(Integer val1, Integer val2) {
		return val1-val2;
	} 

}
