package gameinterface;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import gameinterface.components.TerrainStackVisualizer;
import gamelogic.Terrain;

public class TerrainManagerToolBar extends JToolBar {
	
	private Terrain terrain;
	private TerrainVisualizerPanel visualizer;
	protected TextFullPanel widthField;
	protected TextFullPanel heightField;
	protected TextFieldPanel periodField;
	
	public TerrainManagerToolBar(Terrain terrain) {
		super(null, JToolBar.VERTICAL);
		this.terrain = terrain;
		setUpUI(terrain);
	}
	
	/**
	* Sets up the UI of the element manager
	*/ 
	private void setUpUI(Terrain terrain) {
		add(new JLabel("Terrain"));

		visualizer = new TerrainVisualizerPanel(terrain);
		add(visualizer);
		
		widthField = new TextFullPanel("Width");;
		heightField = new TextFullPanel("Height");
		periodField = new TextFieldPanel("Period");
		periodField.addactionListener( e -> applyToTerrain() );
		
		updateFromTerrain();
		
		add(widthField);
		add(heightField);
		add(periodField);
	}
	
	private void updateFromTerrain() {
		visualizer.repaint();
		widthField.setLabelString(Integer.toString(terrain.getWidth()));
		heightField.setLabelString(Integer.toString(terrain.getHeight()));
		periodField.setFieldString(Integer.toString(terrain.getTriggerTime()));
	}
	
	private void applyToTerrain() {
		try {
			terrain.setTriggerTime(Integer.parseInt(periodField.getFieldString()));
			periodField.setFieldColor(ControlPanel.getStandardFieldColor());
		} catch (Exception e) {
			periodField.setFieldColor(ControlPanel.getWrongFieldColor());		
		}	
	}
}
