package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;

import java.util.List;
import java.util.LinkedList;

/**
* Rectangular visual representation of a node.
* 
* @see Node
*/ 
public class NodeView {

	private int x, y;

	/**
	* @param node
	* @param x
	* @param y
	*/ 
	public NodeView(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	* @return top left x coordinate
	*/ 
	public int getX() { return x; }

	/**
	* @return top left y coordinate
	*/ 
	public int getY() { return y; }

}
