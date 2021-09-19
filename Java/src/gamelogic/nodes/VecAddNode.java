package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Vec2D;

/**
* The node model used to add two 2D vectors.
* 
* @see AddNode
* @see GameManager
*/
public class VecAddNode extends AddNode<Vec2D> {

	/**
	* 
	*/ 
	public VecAddNode() {
		super(Vec2D.class);
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	Vec2D sum(Vec2D val1, Vec2D val2) {
		return Vec2D.add(val1, val2);
	} 

}
