package gameinterface.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

import gamelogic.TerrainLayer;

/**
* This class allows to visualize a list of TerrainLayer from a TerrainStack.
* 
* @see TerrainStackVisualizerView
* @see TerrainLayer
*/
public class TerrainStackVisualizer extends JComponent {
	private TerrainStackVisualizerView view;
	private List<TerrainLayer> stack;
	private int focusedLayer = 0;
	private boolean onlyFocusedInColor = false;
	
	private static Color unfocusedColor  = new Color(192,192,192,192);//Color.lightGray with transparency
	private static Color realFocusedColor  = Color.red;
	private int delta_x = 6;
	private int delta_y = 3;
	private int offsetFactor_y = 5;
	
	
	
	public TerrainStackVisualizer(List<TerrainLayer> stack) {
		view = new TerrainStackVisualizerView();
		this.stack = stack;
	}

	/**
	* @return the dimension the visualizer's parent panel should have
	*/
	public Dimension getParentDimension() {
		int focusLayerHeight = computeParentPreferredHeigth();
		if (focusLayerHeight < 100)
			return new Dimension(computePreferredWidth(), Math.min(computePreferredHeigth(), 100));
		else
			return new Dimension(computePreferredWidth(), focusLayerHeight);
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		view.paint((Graphics2D)g, this);
	}
	
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	@Override
	public Dimension getPreferredSize() {		
		return new Dimension(computePreferredWidth(), computePreferredHeigth());
	}
	/**
	* @return the list of stack represented
	*/ 
	public final List<TerrainLayer> getStack() {
		return stack;
	}
	/**
	* @return the index in list of the layer currently focused
	*/ 
	public int getFocusedLayer() {
		return focusedLayer;
	}
	/**
	* @return if only the focused layer should be displayed with colors
	*/ 
	public boolean getOnlyFocusedInColor() {
		return onlyFocusedInColor;
	}
	/**
	* @return the distance among x to represent a tile side
	*/
	public int getDelta_x() {
		return delta_x;
	}
	/**
	* @return the distance among y to represent a tile side
	*/
	public int getDelta_y() {
		return delta_y;
	}
	/**
	* @return the distance among y betwen two layers
	*/
	public int getOffset_y() {
		return offsetFactor_y * delta_y;
	}
	/**
	* @return the distance among y betwen the focused layer and the one before
	*/
	public int getOffset_Focus_y() {
		if (focusedLayer == 0)
			return 0;
		return stack.get(focusedLayer).getWidth() * delta_y + stack.get(focusedLayer - 1).getHeight() * delta_y;	//+ getOffset_y()
	}
	/**
	* @return the distance among y betwen the position 0 and the first layer
	*/
	public int getOffset_Starting_y() {
		return delta_y * stack.get(0).getWidth() + getOffset_y() * stack.size() + getOffset_Focus_y();
	}
	/**
	* @return the width this should have
	*/
	private int computePreferredWidth() {
		if (stack == null)
			return 0;
		
		int max_wPh = 0;
		for (TerrainLayer layer : stack) {
			max_wPh = Math.max(max_wPh, layer.getWidth() + layer.getHeight());
		}
		return max_wPh * delta_x;
	}
	/**
	* @return the height this should have
	*/
	private int computePreferredHeigth() {
		if (stack == null)
			return 0;
		
		return getOffset_Starting_y() - getOffset_y() + delta_y * stack.get(stack.size() -1).getHeight();
	}
	/**
	* @return the height the its parent panel should have
	*/
	private int computeParentPreferredHeigth() {
		if (stack == null)
			return 10;
		
		int max_wPh = 0;
		for (TerrainLayer layer : stack) {
			max_wPh = Math.max(max_wPh, layer.getWidth() + layer.getHeight());
		}
		return (max_wPh + 5) * delta_y;
	}
	
	
	
	
	
	/**
	* Increases the index of the focused layer.
	*/ 
	public void focusNextLayer() {
		if (focusedLayer < stack.size() -1) {
			focusedLayer++;
			revalidate();
			repaint();
		}
	}
	/**
	* Decreases the index of the focused layer.
	*/
	public void focusPreviousLayer() {
		if (focusedLayer > 0) {
			focusedLayer--;
			revalidate();
			repaint();
		}
	}
	/**
	* Flips if only the focused layer should be displayed with colors.
	*/ 
	public void flipOnlyFocusedInColor() {
		onlyFocusedInColor = !onlyFocusedInColor;		
		repaint();
	}
	
	
	
	
	
	// === Static colors getters ===

	/**
	* @return the color to use for unfocused layers
	*/ 
	public static Color getUnfocusedColor() {
		return unfocusedColor;
	}
	/**
	* @return the color to use for highlighting the borders of the focused layer
	*/
	public static Color getRealFocusedColor() {
		return realFocusedColor;
	}
}
