package gameinterface;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

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

	public ControlPanel() {
		setBackground(Color.red);
		setUpUI();
	}
	
	/**
	* Sets up this panel's UI
	* Creates the elements managers as toolbars
	* Use the main area to display the nodes
	*/ 
	private void setUpUI() {
		setLayout(new BorderLayout());
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
		terrainToolBar = new JToolBar(null, JToolBar.VERTICAL);
		surfaceToolBar = new ElementManagerToolBar<>("Surface", new SurfaceDetailPanel()) { 
			@Override
			public Surface createElement(String name) {
				return new Surface(name);
			}
		};
		speciesToolBar = new ElementManagerToolBar<>("Species", new SpeciesDetailPanel()) { 
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
	
	/**
	* @param The surface array to use on the surface manager
	*/ 
	public void setSurfaces(ArrayList<Surface> surfaceArray) { surfaceToolBar.setElementArray(surfaceArray); }
	/**
	* @param The species array to use on the surface manager
	*/ 
	public void setSpecies(ArrayList<Species> speciesArray) { speciesToolBar.setElementArray(speciesArray); }
}
