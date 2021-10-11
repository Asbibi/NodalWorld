package gameinterface.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import gamelogic.Terrain;

/**
* This class allows to visualize a list of TerrainLayer from a TerrainStack.
* 
* @see TerrainStackVisualizerView
* @see TerrainLayer
*/
public class TerrainStackVisualizer extends JComponent {
	private TerrainStackVisualizerView view;
	private Terrain terrain;
	private int focusedLayer = 0;
	private boolean onlyFocusedInColor = false;
	
	private static Color unfocusedColor  = new Color(192,192,192,192);//Color.lightGray with transparency
	private static Color realFocusedColor  = Color.red;
	private int delta_x = 6;
	private int delta_y = 3;
	private int offsetFactor_y = 5;
	
	private int focusedLayerPositionOnParentScrollBar = 0;
	
	
	
	public TerrainStackVisualizer(Terrain terrain) {
		view = new TerrainStackVisualizerView();
		this.terrain = terrain;
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
	* @return the terrain model
	*/ 
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	* @return the index in list of the layer currently focused
	*/ 
	public int getFocusedLayer() {
		return focusedLayer;
	}

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
		return terrain.getWidth() * delta_y + terrain.getHeight() * delta_y;	//+ getOffset_y()
	}
	/**
	* @return the distance among y betwen the position 0 and the first layer
	*/
	public int getOffset_Starting_y() {
		return delta_y * terrain.getWidth() + getOffset_y() * terrain.getSlots().size() + getOffset_Focus_y();
	}
	/**
	* @return the width this should have
	*/
	private int computePreferredWidth() {
		return (terrain.getWidth()+terrain.getHeight()) * delta_x + 4;	// + 4 is because we have a border with a stroke size of 2 that has a missing part otherwise
	}
	/**
	* @return the height this should have
	*/
	private int computePreferredHeigth() {
		return getOffset_Starting_y() - getOffset_y() + delta_y * terrain.getHeight();
	}
	/** 
	* @return the height the its parent panel should have (0 if there are currently no slots)
	*/
	private int computeParentPreferredHeigth() {
		if (terrain.getSlots().isEmpty())
			return 0;
		
		return (terrain.getWidth() + terrain.getHeight() + 5) * delta_y;
	}
	/**
	* Sets the parent's scroll bar position (the parent should be a JScrollPane) to be centered on the focused layer
	*/
	public void computeFocusedLayerPositionOnParentScrollBar() {
		focusedLayerPositionOnParentScrollBar = (getOffset_Starting_y() - getOffset_y()*(getTerrain().getSlots().size() - focusedLayer)) - getTerrain().getWidth() * delta_y;
		applyFocusedLayerPositionOnParentScrollBar();
	}
	private void applyFocusedLayerPositionOnParentScrollBar() {
		JScrollPane scrollParent = (JScrollPane)(getParent().getParent());
		if (scrollParent == null)
			return;
		
		scrollParent.getVerticalScrollBar().setValue(focusedLayerPositionOnParentScrollBar);
	}
	
	
	
	
	/**
	* Increases the index of the focused layer.
	*/ 
	public void focusNextLayer() {
		if (focusedLayer < terrain.getSlots().size() -1) {
			focusedLayer++;
			revalidate();
			computeFocusedLayerPositionOnParentScrollBar();
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
			computeFocusedLayerPositionOnParentScrollBar();
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
	
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
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
