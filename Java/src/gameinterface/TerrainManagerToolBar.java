package gameinterface;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import gameinterface.components.TerrainStackVisualizer;
import gamelogic.Element;
import gamelogic.Terrain;

/**
* Create an terrain stack manager as a tool bar.
* Includes a visualisation of the different layers as well as property fields for the 
* 
* @see ControlPanel
* @see TerrainVisualizerPanel
* @see Terrain
*/
public class TerrainManagerToolBar extends JToolBar {	
	
	private Terrain terrain;
	private TerrainVisualizerPanel visualizer;
	private TextFixedFieldPanel widthField;
	private TextFixedFieldPanel heightField;
	private TextFieldPanel periodField;
	
	public TerrainManagerToolBar(Terrain terrain) {
		super(null, JToolBar.VERTICAL);
		this.terrain = terrain;
		setUpUI(terrain);
	}
	
	/**
	* Sets up the UI of the terrain manager
	*/ 
	private void setUpUI(Terrain terrain) {
		add(new JLabel("Terrain"));

		visualizer = new TerrainVisualizerPanel(terrain);
		add(visualizer);
		
		widthField = new TextFixedFieldPanel("Width");;
		heightField = new TextFixedFieldPanel("Height");
		periodField = new TextFieldPanel("Period");
		periodField.addActionListener( e -> applyToTerrain() );
		
		updateFromTerrain();
		
		add(widthField);
		add(heightField);
		add(periodField);
	}
	
	
	/**
	* @param terrain
	*/ 
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
		updateFromTerrain();
	}
	
	/**
	* Update the visualisation and the fields value from the terrain reference held (set during instanciation)
	*/ 
	private void updateFromTerrain() {
		if (terrain == null)
			return;
		
		visualizer.repaint();
		widthField.setLabelString(Integer.toString(terrain.getWidth()));
		heightField.setLabelString(Integer.toString(terrain.getHeight()));
		periodField.setFieldString(Integer.toString(terrain.getTriggerTime()));
	}
	
	/**
	* Apply the changes in the fields' values to the terrain reference
	*/ 
	private void applyToTerrain() {
		if (terrain == null)
			return;
		
		try {
			terrain.setTriggerTime(Integer.parseInt(periodField.getFieldString()));
			periodField.setFieldColor(ControlPanel.getStandardFieldColor());
		} catch (Exception e) {
			periodField.setFieldColor(ControlPanel.getWrongFieldColor());		
		}	
	}
}
