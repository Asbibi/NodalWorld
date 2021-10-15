package gameinterface;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.Surface;

/**
* Part of the game window responsible for interacting with the game.<br/>
* It hosts the nodal widget and the element managers.<br/>
* The elements managers are ElementDetailPanel instances.<br/>
* They are derivated from JToolBar which allows the user to freely rearrange their positions.<br/>
* 
* @see ElementManagerToolBar
* @see NodalEditor
* @see GameFrame
*/ 
public class ControlPanel extends JPanel {	
	private JSplitPane splitPanel;
	private JTabbedPane nodeEditorPanel;
	
	private TerrainManagerToolBar terrainToolBar;
	private ElementManagerToolBar<Surface> surfaceToolBar;
	private ElementManagerToolBar<Species> speciesToolBar;
	private PrinterToolBar printerToolBar;

	
	/**
	* @param gameManager the game manager instance to link the control panel on.
	*/ 
	public ControlPanel(GameManager gameManager) {
		setUpUI(gameManager);
	}
	
	
	
	/**
	* Sets up this panel's UI.<br/>
	* Creates the elements and terrain managers as a left toolbar. Its horizontal size can be adjusted by the user.<br/>
	* The main area is used to display the node editors.
	* @param gameManager the game manager instance to link the control panel to.
	*/ 
	private void setUpUI(GameManager gameManager) {
		setLayout(new BorderLayout());
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
		terrainToolBar = new TerrainManagerToolBar(gameManager.getTerrain());
		surfaceToolBar = new ElementManagerToolBar<>(Surface.class, new SurfaceDetailPanel()) { 
			@Override
			public Surface createElement(String name) {
				return new Surface(name);
			}
		};
		speciesToolBar = new ElementManagerToolBar<>(Species.class, new SpeciesDetailPanel()) { 
			@Override
			public Species createElement(String name) {
				return new Species(name);
			}
		};
		printerToolBar = new PrinterToolBar();
		toolBarPanel.add(terrainToolBar);
		toolBarPanel.add(surfaceToolBar);
		toolBarPanel.add(speciesToolBar);
		toolBarPanel.add(printerToolBar);
		JScrollPane toolScrollPanel = new JScrollPane(toolBarPanel);
		toolScrollPanel.setPreferredSize(new Dimension(160,1000));


		
		nodeEditorPanel = NodalEditorBuilder.buildTabbedEditors(gameManager);
		
		
		
		splitPanel = new JSplitPane(SwingConstants.VERTICAL, toolScrollPanel, nodeEditorPanel);
		add(splitPanel);
		splitPanel.setDividerLocation(toolScrollPanel.getPreferredSize().width);
	}
	
	/**
	* Change the gameManager linked to the control panel. Used for loading another world.
	* @param gameManager the game manager instance to link the control panel to.
	*/ 
	public void connectGameManager(GameManager gameManager) {
		nodeEditorPanel = NodalEditorBuilder.buildTabbedEditors(gameManager);
		splitPanel.setRightComponent(nodeEditorPanel);
		terrainToolBar.setTerrain(gameManager.getTerrain());
		surfaceToolBar.setGameManager(gameManager);
		speciesToolBar.setGameManager(gameManager);
	}
	
	/**
	* Update the control panel display only values to make them coherent with a gameManager change.<br/>
	* This is called on GameFrame.update().
	*/ 
	public void update() {
		terrainToolBar.updateFromTerrain(false);
		//surfaceToolBar.updateDetails(); 		// useless since surfaces don't have display only properties
		speciesToolBar.updateDetails();
	}
}
