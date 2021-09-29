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
	JToolBar terrainToolBar;
	ElementManagerToolBar<Surface> surfaceToolBar;
	ElementManagerToolBar<Species> speciesToolBar;

	public ControlPanel(GameManager gameManager) {
		setBackground(Color.red);
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
		terrainToolBar = new JToolBar(null, JToolBar.VERTICAL);
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
		terrainToolBar.add(new JButton("Test"));
		toolBarPanel.add(terrainToolBar);
		toolBarPanel.add(surfaceToolBar);
		toolBarPanel.add(speciesToolBar);
		add(toolBarPanel, BorderLayout.WEST);
	}
}
