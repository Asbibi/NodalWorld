package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Vec2D;

/**
* The node model used to substract two 2D vectors.
* 
* @see SubNode
* @see GameManager
*/
public class VecSubNode extends SubNode<Vec2D> {

	/**
	* 
	*/ 
	public VecSubNode() {
		super(Vec2D.class);
	}

	/**
	* @param val1
	* @param val2
	* @return the difference between val1 and val2
	*/ 
	Vec2D sub(Vec2D val1, Vec2D val2) {
		return Vec2D.sub(val1, val2);
	} 

}
