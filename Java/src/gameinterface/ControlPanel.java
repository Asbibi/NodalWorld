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
* @see GameFrame
*/ 
public class ControlPanel extends JPanel {
	static private Color standardFieldColor = Color.white;
	static private Color wrongFieldColor = Color.red;
	
	private JSplitPane splitPanel;
	private JTabbedPane nodeEditorPanel;
	
	private TerrainManagerToolBar terrainToolBar;
	private ElementManagerToolBar<Surface> surfaceToolBar;
	private ElementManagerToolBar<Species> speciesToolBar;

	
	public ControlPanel(GameManager gameManager) {
		setUpUI(gameManager);
	}
	
	
	
	/**
	* Sets up this panel's UI
	* Creates the elements and terrain managers as a left toolbar. Its horizontal size can be adjusted by the user
	* The main area is used to display the node editors.
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
		toolBarPanel.add(terrainToolBar);
		toolBarPanel.add(surfaceToolBar);
		toolBarPanel.add(speciesToolBar);
		JScrollPane toolScrollPanel = new JScrollPane(toolBarPanel);
		toolScrollPanel.setPreferredSize(new Dimension(160,1000));


		
		nodeEditorPanel = NodalEditorBuilder.buildTabbedEditors(gameManager);
		
		
		
		splitPanel = new JSplitPane(SwingConstants.VERTICAL, toolScrollPanel, nodeEditorPanel);
		add(splitPanel);
		splitPanel.setDividerLocation(toolScrollPanel.getPreferredSize().width);
	}
	public  void connectGameManager(GameManager gameManager) {
		nodeEditorPanel = NodalEditorBuilder.buildTabbedEditors(gameManager);
		splitPanel.setRightComponent(nodeEditorPanel);
		terrainToolBar.setTerrain(gameManager.getTerrain());
		surfaceToolBar.setGameManager(gameManager);
		speciesToolBar.setGameManager(gameManager);
	}
	
	
	/**
	* @return the color to use on a TextField background by default
	*/ 
	public static Color getStandardFieldColor() {
		return standardFieldColor;
	}
	/**
	* @return the color to use on a TextField background when the string inputed doesn't comply with constraints (e.g. writting letters in a TextField used for inputing integers)
	*/
	public static Color getWrongFieldColor() {
		return wrongFieldColor;
	}
}
