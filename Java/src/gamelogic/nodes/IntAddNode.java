package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;

/**
* The node model used to add two integers.
* 
* @see GameManager
*/
public class IntAddNode extends AddNode<Integer> {

	/**
	* 
	*/ 
	public IntAddNode() {
		super(Integer.class);
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	Integer sum(Integer val1, Integer val2) {
		return val1+val2;
	} 

}
