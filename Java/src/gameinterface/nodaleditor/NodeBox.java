package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;
import gameinterface.NodalEditor;
import gamelogic.Vec2D;
import gamelogic.Surface;
import gamelogic.TerrainModel;
import gamelogic.Species;
import gamelogic.Entity;

import java.awt.Graphics2D;
import java.awt.FontMetrics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;

import java.util.Collection;
import java.util.LinkedList;

import java.io.Serializable;

import java.lang.Class;

/**
* Geometric representation and interaction methods for a node in the nodal editor.
* 
* @see Node
* @see Port
*/ 
public class NodeBox implements Serializable {

	private Node node;
	private double x, y, scale;

	private double padding;
	private double width, height;
	private Collection<Port> ports;
	private boolean valid;

	private double tx, ty, ds;

	/**
	* @param node
	* @param x
	* @param y
	*/ 
	public NodeBox(Node node, double x, double y, double scale) {
		this.node = node;
		this.x = x;
		this.y = y;
		this.scale = scale;

		width = 0;
		height = 0;
		ports = new LinkedList<Port>();
		valid = false;

		tx = 0;
		ty = 0;
		ds = 0;
	}

	/**
	* @param padding
	* @param spacing
	* @param portRadius
	* @param metrics
	*/ 
	public void init(double padding, double spacing, double portRadius, FontMetrics metrics) {
		// Padding
		this.padding = padding;

		// Box dimensions
		double lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();
		double spaceWidth = metrics.stringWidth("    ");
		double offset = lineHeight+spacing;
		double maxWidth, dy;

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

		double titleWidth = metrics.stringWidth(node.toString()) + 2*spaceWidth;
		width = Math.max(width, titleWidth);

		// Ports
		dy = lineHeight; // reserve one line for node's name
		for(Input input : node.getInputs()) {
			dy += offset;
			ports.add(new Port(this, x-padding, y+dy, scale, portRadius, input));
		}

		dy = lineHeight; // reserve one line for node's name
		for(Output output : node.getOutputs()) {
			dy += offset;
			ports.add(new Port(this, x+width+padding, y+dy, scale, portRadius, output));
		}

		// Valid geometry and variables
		valid = true;
	}

	/**
	* @return node represented by this box
	*/ 
	public Node getNode() { return node; }

	public double getScale() { return scale*(1+ds); }

	/**
	* @return top left x coordinate
	*/ 
	public double getX() { return x*getScale() + tx; }

	/**
	* @return top left y coordinate
	*/ 
	public double getY() { return y*getScale() + ty; }

	/**
	* @param tx
	* @param ty
	*/ 
	public void translate(double tx, double ty) {
		this.tx = tx;
		this.ty = ty;
		for(Port port : ports) port.translate(tx, ty);
	}

	public void commitTranslate() {
		x += tx/getScale();
		y += ty/getScale();
		tx = 0;
		ty = 0;
		for(Port port : ports) port.commitTranslate();
	}

	public void scale(double ds) {
		this.ds = ds;
		for(Port port : ports) port.scale(ds);
	}

	public void commitScale() {
		this.scale *= 1+ds;
		ds = 0; 
		for(Port port : ports) port.commitScale();
	}

	/**
	* @return true if the view has been initialized, otherwise false
	*/
	public boolean isValid() { return valid; }

	/**
	* @return the padding on left and right of the box
	*/ 
	public double getPadding() { return padding*getScale(); }

	/**
	* @return box width
	*/ 
	public double getWidth() { return width*getScale(); }

	/**
	* @return box height
	*/ 
	public double getHeight() { return height*getScale(); }

	/**
	* @return the ports of the box
	*/ 
	public Collection<Port> getPorts() { return ports; }

	/**
	* @return true if the pos is inside the box, otherwise false
	*/ 
	public boolean hit(double xPos, double yPos) {
		return (xPos >= getX()-getPadding() 
				&& xPos <= getX()+getWidth()+getPadding() 
				&& yPos >= getY() 
				&& yPos <= getY()+getHeight());
	}

	public void paint(Graphics2D g2d, NodalEditor editor) {
		FontMetrics metrics = g2d.getFontMetrics();
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();

		RoundRectangle2D rect = new RoundRectangle2D.Double(getX()-getPadding(), getY(), getWidth()+2*getPadding(), getHeight(), 2*getPadding(), 2*getPadding());
		if(editor.isSelected(this)) {
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.pink);
			g2d.draw(rect);
		}
		g2d.setColor(Color.lightGray);
		g2d.fill(rect);

		g2d.setColor(Color.black);
		int titleWidth = metrics.stringWidth(node.toString());
		g2d.drawString(node.toString(), (int) (getX()+(getWidth()-titleWidth)/2), (int) (getY()+lineHeight));

		for(Port port : ports) {
			g2d.setColor(port.getColor());
			g2d.fill(portShape(port));

			g2d.setColor(Color.black);
			if(port.hasInput()) {
				g2d.drawString(port.getInput().toString(), (int) getX(), (int) (port.getY()));
			} else {
				int wordWidth = metrics.stringWidth(port.getOutput().toString());
				g2d.drawString(port.getOutput().toString(), (int) (getX()+getWidth()-wordWidth), (int) (port.getY()));
			}
		}
	}


	// ========== PRIVATE UTILITY METHODS ==========

	private Shape portShape(Port port) {
		if(port.hasInput() && !port.getInput().hasSource()) {
			Class<?> dataClass = port.getInput().getDataClass();
			if(dataClass != Boolean.class && dataClass != Integer.class && dataClass != Double.class && dataClass != Surface.class) 
				return new Rectangle2D.Double(port.getX()-port.getSize(), port.getY()-port.getSize(), 2*port.getSize(), 2*port.getSize());
		}

		return new Ellipse2D.Double(port.getX()-port.getSize(), port.getY()-port.getSize(), 2*port.getSize(), 2*port.getSize());
	}
}
