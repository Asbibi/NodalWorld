package gameinterface;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import gamelogic.Terrain;

/**
* ToolBar for viewing and editing the terrain.<br/>
* It includes a visualizer of the different models and buttons to re-arrange them, delete one or add one.<br/>
* It also displays the dimensions of the terrain as well as the terrain trigger time (that last one can be modified)
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
	
	/**
	* @param terrain The terrain represented
	*/ 
	public TerrainManagerToolBar(Terrain terrain) {
		super(null, JToolBar.VERTICAL);
		this.terrain = terrain;
		setUpUI(terrain);
	}
	
	/**
	* Creates the UI, called by the conrstructor 
	* @param terrain The terrain represented
	*/ 
	private void setUpUI(Terrain terrain) {
		add(new JLabel("Terrain"));

		visualizer = new TerrainVisualizerPanel(terrain);
		add(visualizer);
		
		widthField = new TextFixedFieldPanel("Width");;
		heightField = new TextFixedFieldPanel("Height");
		periodField = new TextFieldPanel("Period");
		periodField.addActionListener( e -> applyToTerrain() );
		
		updateFromTerrain(true);
		
		add(widthField);
		add(heightField);
		add(periodField);
	}
	
	
	/**
	* @param terrain The terrain represented
	*/ 
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
		visualizer.setTerrain(this.terrain);
		updateFromTerrain(true);
	}
	
	/**
	* Update the visualisation and the fields value from the terrain reference held.<br/>
	* Should be called with complete on true when following a terrain reference change, otherwise with false to keep periodField modified string intact.
	* @param completeUpdate indicates if all fields must be updated (true) or just the display only component (false) 
	*/ 
	public void updateFromTerrain(boolean complete) {
		if (terrain == null)
			return;
		
		visualizer.repaint();
		widthField.setLabelString(Integer.toString(terrain.getWidth()));
		heightField.setLabelString(Integer.toString(terrain.getHeight()));
		if (complete)
			periodField.setFieldString(Integer.toString(terrain.getTriggerTime()));
	}
	
	/**
	* Apply the changes in the fields' values (i.e. periodField) to the terrain reference.
	*/ 
	private void applyToTerrain() {
		if (terrain == null)
			return;
		
		try {
			terrain.setTriggerTime(Integer.parseInt(periodField.getFieldString()));
			periodField.setFieldColor(GameFrame.getStandardFieldColor());
		} catch (Exception e) {
			periodField.setFieldColor(GameFrame.getWrongFieldColor());		
		}	
	}
}
