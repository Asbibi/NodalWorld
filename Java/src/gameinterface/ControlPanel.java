package gameinterface;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.Surface;

/**
* Part of the game window responsible for interacting with the game.
* It hosts the nodal widget and the element managers.
* The elements managers are ElementDetailPanel instances.
* They are derivated from JToolBar which allows the user to freely rearrange their positions
* 
* @see ElementManagerToolBar
*/ 
public class ControlPanel extends JPanel {
	static private Color standardFieldColor = Color.white;
	static private Color wrongFieldColor = Color.red;
	
	private JPanel nodeEditorPanel;
	
	private JToolBar terrainToolBar;
	private ElementManagerToolBar<Surface> surfaceToolBar;
	private ElementManagerToolBar<Species> speciesToolBar;

	public ControlPanel(GameManager gameManager) {
		setUpUI(gameManager);
	}
	
	/**
	* Sets up this panel's UI
	* Creates the elements managers as toolbars
	* Use the main area to display the nodes
	*/ 
	private void setUpUI(GameManager gameManager) {
		setLayout(new BorderLayout());
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
		terrainToolBar = new TerrainManagerToolBar(gameManager.getTerrainStack());
		surfaceToolBar = new ElementManagerToolBar<>("Surface", gameManager.getSurfaceArray(), new SurfaceDetailPanel()) { 
			@Override
			public Surface createElement(String name) {
				return new Surface(name);
			}
		};
		speciesToolBar = new ElementManagerToolBar<>("Species", gameManager.getSpeciesArray(), new SpeciesDetailPanel()) { 
			@Override
			public Species createElement(String name) {
				return new Species(name, "");
			}
		};
		toolBarPanel.add(terrainToolBar);
		toolBarPanel.add(surfaceToolBar);
		toolBarPanel.add(speciesToolBar);
		JScrollPane toolScrollPanel = new JScrollPane(toolBarPanel);
		toolScrollPanel.setPreferredSize(new Dimension(160,1000));


		
		nodeEditorPanel = new JPanel();
		nodeEditorPanel.setBackground(Color.red);
		
		
		
		JSplitPane splitPanel = new JSplitPane(SwingConstants.VERTICAL, toolScrollPanel, nodeEditorPanel);
		add(splitPanel);
		splitPanel.setDividerLocation(toolScrollPanel.getPreferredSize().width);
		//add(toolScrollPanel, BorderLayout.WEST);
	}

	
	
	
	public static Color getStandardFieldColor() {
		return standardFieldColor;
	}

	public static Color getWrongFieldColor() {
		return wrongFieldColor;
	}

	public static void setStandardFieldColor(Color standardFieldColor) {
		ControlPanel.standardFieldColor = standardFieldColor;
	}

	public static void setWrongFieldColor(Color wrongFieldColor) {
		ControlPanel.wrongFieldColor = wrongFieldColor;
	}
}
