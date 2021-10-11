package gameinterface.nodaleditor;

import java.io.Serializable;

import gamelogic.Input;
import gamelogic.Output;

/**
* Geometric representation and interaction methods for an input or output in the nodal editor.
* 
* @see Input
* @see Output
* @see NodeBox
*/ 
public class Port implements Serializable {

	private NodeBox parent;
	private double x, y, scale, size;
	private Input input;
	private Output output;

	private double tx, ty, ds;

	/**
	* @param parent
	* @param x
	* @param y
	*/ 
	public Port(NodeBox parent, double x, double y, double scale, double size) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.size = size;
		input = null;
		output = null;

		tx = 0;
		ty = 0;
		ds = 0;
	}

	/**
	* @param parent
	* @param x
	* @param y
	* @param input
	*/ 
	public Port(NodeBox parent, double x, double y, double scale, double size, Input input) {
		this(parent, x, y, scale, size);
		this.input = input;
	}

	/**
	* @param parent
	* @param x
	* @param y
	* @param output
	*/ 
	public Port(NodeBox parent, double x, double y, double scale, double size, Output output) {
		this(parent, x, y, scale, size);
		this.output = output;
	}

	/**
	* @return the parent node box
	*/ 
	public NodeBox getBox() { return parent; }

	public double getScale() { return scale*(1+ds); }

	/**
	* @return x coordinate
	*/ 
	public double getX() { return x*getScale() + tx; }

	/**
	* @return y coordinate
	*/ 
	public double getY() { return y*getScale() + ty; }

	/**
	* @param tx
	* @param ty
	*/ 
	public void translate(double tx, double ty) {
		this.tx = tx;
		this.ty = ty;
	}

	public void commitTranslate() {
		x += tx/getScale();
		y += ty/getScale();
		tx = 0;
		ty = 0;
	}

	public void scale(double ds) {
		this.ds = ds;
	}

	public void commitScale() {
		this.scale *= 1+ds;
		ds = 0;
	}

	/**
	* @return the port size
	*/ 
	public double getSize() { return size*getScale(); }

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
	public boolean hit(double xPos, double yPos) {
		return ((getX()-xPos)*(getX()-xPos)+(getY()-yPos)*(getY()-yPos) <= getSize()*getSize());
	}

}
