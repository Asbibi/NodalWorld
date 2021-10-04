package gameinterface.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

import gamelogic.Terrain;

public class TerrainStackVisualizer extends JComponent {
	private TerrainStackVisualizerView view;
	private Terrain terrain;
	private int focusedLayer = 0;
	private boolean onlyFocusedInColor = false;
	
	private static Color unfocusedColor  = new Color(192,192,192,192);//Color.lightGray;//
	private static Color realFocusedColor  = Color.red;
	private int delta_x = 6;
	private int delta_y = 3;
	private int offsetFactor_y = 5;
	private int offsetFactor_Focus_y = 12;
	
	
	
	
	public TerrainStackVisualizer(Terrain terrain) {
		view = new TerrainStackVisualizerView();
		this.terrain = terrain;
	}
	
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

	public Terrain getTerrain() {
		return terrain;
	}
	public int getFocusedLayer() {
		return focusedLayer;
	}

	public void focusNextLayer() {
		if (focusedLayer < terrain.getSlots().size() -1) {
			focusedLayer++;
			revalidate();
			repaint();
		}
	}
	public void focusPreviousLayer() {
		if (focusedLayer > 0) {
			focusedLayer--;
			revalidate();
			repaint();
		}
	}

	public boolean getOnlyFocusedInColor() {
		return onlyFocusedInColor;
	}

	public void flipOnlyFocusedInColor() {
		onlyFocusedInColor = !onlyFocusedInColor;		
		repaint();
	}

	
	public int getDelta_x() {
		return delta_x;
	}

	public int getDelta_y() {
		return delta_y;
	}

	public int getOffset_y() {
		return offsetFactor_y * delta_y;
	}

	public int getOffset_Focus_y() {
		if (focusedLayer == 0)
			return 0;
		return terrain.getWidth() * delta_y + terrain.getHeight() * delta_y;	//+ getOffset_y()
	}
	
	public int getOffset_Starting_y() {
		return delta_y * terrain.getWidth() + getOffset_y() * terrain.getSlots().size() + getOffset_Focus_y();
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
	private int computePreferredWidth() {
		return (terrain.getWidth()+terrain.getHeight()) * delta_x;
	}
	private int computePreferredHeigth() {
		return getOffset_Starting_y() - getOffset_y() + delta_y * terrain.getHeight();
	}
	private int computeParentPreferredHeigth() {
		return (terrain.getWidth() + terrain.getHeight() + 5) * delta_y;
	}
	

	
	// === Static colors getters ===

	public static Color getUnfocusedColor() {
		return unfocusedColor;
	}

	public static Color getRealFocusedColor() {
		return realFocusedColor;
	}
}
