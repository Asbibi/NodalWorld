package gameinterface.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

import gamelogic.TerrainLayer;

public class TerrainStackVisualizer extends JComponent {
	private TerrainStackVisualizerView view;
	private List<TerrainLayer> stack;
	private int focusedLayer = 0;
	private boolean onlyFocusedInColor = false;
	
	private static Color unfocusedColor  = new Color(192,192,192,192);//Color.lightGray;//
	private static Color realFocusedColor  = Color.red;
	private int delta_x = 6;
	private int delta_y = 3;
	private int offsetFactor_y = 5;
	private int offsetFactor_Focus_y = 12;
	
	
	
	
	public TerrainStackVisualizer(List<TerrainLayer> stack) {
		view = new TerrainStackVisualizerView();
		this.stack = stack;
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

	public final List<TerrainLayer> getStack() {
		return stack;
	}
	public int getFocusedLayer() {
		return focusedLayer;
	}

	public void focusNextLayer() {
		if (focusedLayer < stack.size() -1) {
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
		return stack.get(focusedLayer).getWidth() * delta_y + stack.get(focusedLayer - 1).getHeight() * delta_y;	//+ getOffset_y()
	}
	
	public int getOffset_Starting_y() {
		return delta_y * stack.get(0).getWidth() + getOffset_y() * stack.size() + getOffset_Focus_y();
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
		if (stack == null)
			return 0;
		
		int max_wPh = 0;
		for (TerrainLayer layer : stack) {
			max_wPh = Math.max(max_wPh, layer.getWidth() + layer.getHeight());
		}
		return max_wPh * delta_x;
	}
	private int computePreferredHeigth() {
		if (stack == null)
			return 0;
		
		return getOffset_Starting_y() - getOffset_y() + delta_y * stack.get(stack.size() -1).getHeight();
	}
	private int computeParentPreferredHeigth() {
		if (stack == null)
			return 10;
		
		int max_wPh = 0;
		for (TerrainLayer layer : stack) {
			max_wPh = Math.max(max_wPh, layer.getWidth() + layer.getHeight());
		}
		return (max_wPh + 5) * delta_y;
	}
	

	
	// === Static colors getters ===

	public static Color getUnfocusedColor() {
		return unfocusedColor;
	}

	public static Color getRealFocusedColor() {
		return realFocusedColor;
	}
}
