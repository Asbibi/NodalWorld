package gameinterface.nodaleditor;

import gamelogic.Input;
import gamelogic.Output;

/**
*
*/ 
public class Port {

	private NodeBox parent;
	private int x, y, size;
	private Input input;
	private Output output;

	/**
	* @param parent
	* @param x
	* @param y
	*/ 
	public Port(NodeBox parent, int x, int y, int size) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.size = size;
		input = null;
		output = null;
	}

	/**
	* @param parent
	* @param x
	* @param y
	* @param input
	*/ 
	public Port(NodeBox parent, int x, int y, int size, Input input) {
		this(parent, x, y, size);
		this.input = input;
	}

	/**
	* @param parent
	* @param x
	* @param y
	* @param output
	*/ 
	public Port(NodeBox parent, int x, int y, int size, Output output) {
		this(parent, x, y, size);
		this.output = output;
	}

	/**
	* @return the parent node box
	*/ 
	public NodeBox getBox() { return parent; }

	/**
	* @return x coordinate
	*/ 
	public int getX() { return x; }

	/**
	* @return y coordinate
	*/ 
	public int getY() { return y; }

	/**
	* @param dx
	* @param dy
	*/ 
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}

	/**
	* @return the port size
	*/ 
	public int getSize() { return size; }

	/**
	* @return the port corresponds to an input
	*/ 
	public boolean hasInput() { return (input != null); }

	/**
	* @return the corresponding input
	*/ 
	public Input getInput() { return input; }

	/**
	* @return the port corresponds to an output
	*/ 
	public boolean hasOutput() { return (output != null); }

	/**
	* @return the corresponding output
	*/ 
	public Output getOutput() { return output; }

	/**
	* @return true if the pos is at a distance smaller than size to the port, otherwise false
	*/ 
	public boolean hit(int xPos, int yPos) {
		return ((x-xPos)*(x-xPos)+(y-yPos)*(y-yPos) <= size*size);
	}

}
