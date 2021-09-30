package gameinterface;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import gameinterface.components.TerrainStackVisualizer;
import gamelogic.TerrainStack;

public class TerrainManagerToolBar extends JToolBar {
	
	private TerrainStack terrain;
	private TerrainVisualizerPanel visualizer;
	protected TextFullPanel widthField;
	protected TextFullPanel heightField;
	protected TextFieldPanel periodField;
	
	public TerrainManagerToolBar(TerrainStack terrain) {
		super(null, JToolBar.VERTICAL);
		this.terrain = terrain;
		setUpUI(terrain);
	}
	
	/**
	* Sets up the UI of the element manager
	*/ 
	private void setUpUI(TerrainStack terrain) {
		add(new JLabel("Terrain"));

		visualizer = new TerrainVisualizerPanel(terrain.getStack());
		add(visualizer);
		
		widthField = new TextFullPanel("Width");;
		heightField = new TextFullPanel("Height");
		periodField = new TextFieldPanel("Period");
		
		updateFromTerrain();
		
		add(widthField);
		add(heightField);
		add(periodField);
	}
	
	private void updateFromTerrain() {
		visualizer.repaint();
		widthField.setLabelString(Integer.toString(terrain.getStackDimension().width));
		heightField.setLabelString(Integer.toString(terrain.getStackDimension().height));
		periodField.setFieldString(Integer.toString(terrain.getTriggerTime()));
	}
}
