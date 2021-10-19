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
	private TextFieldPanel widthField;
	private TextFieldPanel heightField;
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
		
		widthField = new TextFieldPanel("Width");;
		heightField = new TextFieldPanel("Height");
		periodField = new TextFieldPanel("Period");
		widthField.addActionListener( e -> applyWidthToTerrain() );
		heightField.addActionListener( e -> applyHeightToTerrain() );
		periodField.addActionListener( e -> applyPeriodToTerrain() );
		
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
		if (complete) {
			widthField.setFieldString(Integer.toString(terrain.getWidth()));
			heightField.setFieldString(Integer.toString(terrain.getHeight()));			
			periodField.setFieldString(Integer.toString(terrain.getTriggerTime()));		
		}		
	}
	
	/**
	* Apply the changes in the period field value to the terrain reference.
	*/ 
	private void applyWidthToTerrain() {
		if (terrain == null)
			return;
		
		try {
			int val = Integer.parseInt(widthField.getFieldString());
			if (val <1) {
				widthField.setFieldColor(GameFrame.getWrongFieldColor());	
				return;
			}
			terrain.setWidth(val);
			widthField.setFieldColor(GameFrame.getStandardFieldColor());
		} catch (Exception e) {
			widthField.setFieldColor(GameFrame.getWrongFieldColor());		
		}	
	}
	
	/**
	* Apply the changes in the period field value to the terrain reference.
	*/ 
	private void applyHeightToTerrain() {
		if (terrain == null)
			return;
		
		try {
			int val = Integer.parseInt(heightField.getFieldString());
			if (val <1) {
				heightField.setFieldColor(GameFrame.getWrongFieldColor());	
				return;
			}
			terrain.setHeight(val);
			heightField.setFieldColor(GameFrame.getStandardFieldColor());
		} catch (Exception e) {
			heightField.setFieldColor(GameFrame.getWrongFieldColor());		
		}	
	}
	
	/**
	* Apply the changes in the period field value to the terrain reference.
	*/ 
	private void applyPeriodToTerrain() {
		if (terrain == null)
			return;
		
		try {
			int val = Integer.parseInt(periodField.getFieldString());
			if (val <1) {
				periodField.setFieldColor(GameFrame.getWrongFieldColor());	
				return;
			}
			terrain.setTriggerTime(val);
			periodField.setFieldColor(GameFrame.getStandardFieldColor());
		} catch (Exception e) {
			periodField.setFieldColor(GameFrame.getWrongFieldColor());		
		}	
	}
}
