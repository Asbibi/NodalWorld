package gameinterface.nodaleditor;

import java.awt.Color;
import java.io.Serializable;

import gamelogic.Entity;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* Geometric representation and interaction methods for an input or output in the nodal editor.
* 
* @see Input
* @see Output
* @see NodeBox
*/ 
public class Port implements Serializable {


	// ========== MEMBER VARIABLES ==========

	private NodeBox parent;
	private double x, y, scale, size;
	private Input input;
	private Output output;

	private double tx, ty, ds;


	// ========== INITIALIZATION ==========

	/**
	* @param parent
	* @param x
	* @param y
	* @param scale
	* @param size
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
	* @param scale
	* @param size
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
	* @param scale
	* @param size
	* @param output
	*/ 
	public Port(NodeBox parent, double x, double y, double scale, double size, Output output) {
		this(parent, x, y, scale, size);
		this.output = output;
	}


	// ========== BASIC GETTERS AND SETTERS ==========

	/**
	* @return the parent node box
	*/ 
	public NodeBox getBox() { return parent; }

	/**
	* @return the current scale
	*/ 
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


	// ========== TRANSLATION AND SCALING ==========

	/**
	* Set the current translation
	*  
	* @param tx
	* @param ty
	*/ 
	public void translate(double tx, double ty) {
		this.tx = tx;
		this.ty = ty;
	}

	/**
	* Record the current translation as permanent
	*/ 
	public void commitTranslate() {
		x += tx/getScale();
		y += ty/getScale();
		tx = 0;
		ty = 0;
	}

	/**
	* Set the current scaling
	* 
	* @param ds
	*/ 
	public void scale(double ds) {
		this.ds = ds;
	}

	/**
	* Record the current scaling as permanent
	*/ 
	public void commitScale() {
		this.scale *= 1+ds;
		ds = 0;
	}


	// ========== HIT TESTING FOR USER INTERACTION ==========

	/**
	* @return true if the pos is at a distance smaller than size to the port, otherwise false
	*/ 
	public boolean hit(double xPos, double yPos) {
		return ((getX()-xPos)*(getX()-xPos)+(getY()-yPos)*(getY()-yPos) <= getSize()*getSize());
	}


	// ========== PAINTING ==========
	
	/**
	* @return The color to use to represent the port
	*/ 
	public Color getColor() {
		Class<?> dataClass = hasInput() ? getInput().getDataClass() : getOutput().getDataClass();

		if(dataClass == Boolean.class) {				return boolColor;
		} else if(dataClass == Integer.class) {			return intColor;
		} else if(dataClass == Double.class) {			return doubleColor;
		} else if(dataClass == Vec2D.class) {			return vectColor;
		} else if(dataClass == Surface.class) {			return surfaceColor;
		} else if(dataClass == TerrainModel.class) {	return slotColor;
		} else if(dataClass == Species.class) {			return speciesColor;
		} else if(dataClass == Entity.class) {			return entityColor;
		}
		return otherColor;
	}


	// ===== Static colors =====
	final private static Color boolColor = new Color(213,52,44);
	final private static Color intColor = new Color(35,150,75);
	final private static Color doubleColor = new Color(0,225,174);
	final private static Color vectColor = new Color(245,190,15);
	final private static Color surfaceColor = new Color(189,108,240);
	final private static Color slotColor = new Color(255,68,255);
	final private static Color speciesColor = new Color(80,118,156);
	final private static Color entityColor = new Color(59,173,255);
	final private static Color otherColor = new Color(148,115,118);
}
