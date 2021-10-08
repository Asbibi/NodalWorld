package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;

import java.awt.FontMetrics;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
* Geometric representation and interaction methods for a node in the nodal editor.
* 
* @see Node
* @see Port
*/ 
public class NodeBox implements Serializable {

	private Node node;
	private int x, y;

	private int padding;
	private int width, height;
	private Collection<Port> ports;
	private boolean valid;

	/**
	* @param node
	* @param x
	* @param y
	*/ 
	public NodeBox(Node node, int x, int y) {
		this.node = node;
		this.x = x;
		this.y = y;

		width = 0;
		height = 0;
		ports = new LinkedList<Port>();
		valid = false;
	}

	/**
	* @param padding
	* @param spacing
	* @param portRadius
	* @param metrics
	*/ 
	public void init(int padding, int spacing, int portRadius, FontMetrics metrics) {
		// Padding
		this.padding = padding;

		// Box dimensions
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();
		int spaceWidth = metrics.stringWidth("    ");
		int offset = lineHeight+spacing;
		int maxWidth, dy;

		width = spaceWidth;
		height = 0;

		maxWidth = 0;
		dy = lineHeight; // reserve one line for node's name
		for(Input input : node.getInputs()) {
			maxWidth = Math.max(maxWidth, metrics.stringWidth(input.toString()));
			dy += offset;
		}
		dy += lineHeight; // reserve space for the last line
		width += maxWidth;
		height = Math.max(height, dy);

		maxWidth = 0;
		dy = lineHeight; // reserve one line for node's name
		for(Output output : node.getOutputs()) {
			maxWidth = Math.max(maxWidth, metrics.stringWidth(output.toString()));
			dy += offset;
		}
		dy += lineHeight; // reserve space for the last line
		width += maxWidth;
		height = Math.max(height, dy);

		int titleWidth = metrics.stringWidth(node.toString()) + 2*spaceWidth;
		width = Math.max(width, titleWidth);

		// Ports
		dy = lineHeight; // reserve one line for node's name
		for(Input input : node.getInputs()) {
			dy += offset;
			ports.add(new Port(this, x-padding, y+dy, portRadius, input));
		}

		dy = lineHeight; // reserve one line for node's name
		for(Output output : node.getOutputs()) {
			dy += offset;
			ports.add(new Port(this, x+width+padding, y+dy, portRadius, output));
		}

		// Valid geometry and variables
		valid = true;
	}

	/**
	* @return node represented by this box
	*/ 
	public Node getNode() { return node; }

	/**
	* @return top left x coordinate
	*/ 
	public int getX() { return x; }

	/**
	* @return top left y coordinate
	*/ 
	public int getY() { return y; }

	/**
	* @param dx
	* @param dy
	*/ 
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
		for(Port port : ports) port.translate(dx, dy);
	}

	/**
	* @return true if the view has been initialized, otherwise false
	*/
	public boolean isValid() { return valid; }

	/**
	* @return the padding on left and right of the box
	*/ 
	public int getPadding() { return padding; }

	/**
	* @return box width
	*/ 
	public int getWidth() { return width; }

	/**
	* @return box height
	*/ 
	public int getHeight() { return height; }

	/**
	* @return the ports of the box
	*/ 
	public Collection<Port> getPorts() { return ports; }

	/**
	* @return true if the pos is inside the box, otherwise false
	*/ 
	public boolean hit(int xPos, int yPos) {
		return (xPos >= x-padding && xPos <= x+width+padding && yPos >= y && yPos <= y+height);
	}

}
