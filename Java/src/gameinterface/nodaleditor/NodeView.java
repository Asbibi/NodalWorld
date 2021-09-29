package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;

import java.awt.FontMetrics;

import java.util.Map;
import java.util.HashMap;

/**
* Rectangular visual representation of a node.
* 
* @see Node
*/ 
public class NodeView {

	private int x, y;
	private int offset;
	private int width, height;
	private int portRadius;
	private Map<Input, Integer> inputOffset;
	private Map<Output, Integer> outputOffset;
	private boolean valid;

	/**
	* @param node
	* @param x
	* @param y
	*/ 
	public NodeView(int x, int y) {
		this.x = x;
		this.y = y;
		offset = 0;
		width = 0;
		height = 0;
		inputOffset = new HashMap<Input, Integer>();
		outputOffset = new HashMap<Output, Integer>();
		valid = false;
	}

	/**
	* @param node
	* @param metrics
	*/ 
	public void initView(Node node, FontMetrics metrics, int spacing, int radius) {
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();
		int spaceWidth = metrics.stringWidth("    ");
		offset = lineHeight+spacing;

		portRadius = radius;

		width = spaceWidth;
		height = 0;
		int maxWidth, dy;

		maxWidth = 0;
		dy = offset; // reserve one line for node's name
		for(Input input : node.getInputs()) {
			maxWidth = Math.max(maxWidth, metrics.stringWidth(input.toString()));
			dy += offset;
			inputOffset.put(input, dy);
		}
		dy += offset; // reserve space for the last line
		width += maxWidth;
		height = Math.max(height, dy);

		maxWidth = 0;
		dy = offset; // reserve one line for node's name
		for(Output output : node.getOutputs()) {
			maxWidth = Math.max(maxWidth, metrics.stringWidth(output.toString()));
			dy += offset;
			outputOffset.put(output, dy);
		}
		dy += offset; // reserve space for the last line
		width += maxWidth;
		height = Math.max(height, dy);

		int titleWidth = metrics.stringWidth(node.toString()) + 2*spaceWidth;
		width = Math.max(width, titleWidth);

		valid = true;
	}

	/**
	* @return true if the view has been initialized, otherwise false
	*/
	public boolean isValid() { return valid; }

	/**
	* @return top left x coordinate
	*/ 
	public int getX() { return x; }

	/**
	* @return top left y coordinate
	*/ 
	public int getY() { return y; }

	/**
	* @return box width
	*/ 
	public int getWidth() { return width; }

	/**
	* @return box height
	*/ 
	public int getHeight() { return height; }

	/**
	* @return port radius
	*/ 
	public int getPortRadius() { return portRadius; }

	/**
	* @param input
	* @return the input y offset in the box
	*/ 
	public int getInputOffset(Input input) { return inputOffset.get(input); }

	/**
	* @param input
	* @return the output y offset in the box
	*/ 
	public int getOutputOffset(Output output) { return outputOffset.get(output); }

}
