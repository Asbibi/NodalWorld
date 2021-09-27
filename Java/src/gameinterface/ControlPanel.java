package gameinterface;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import gamelogic.Species;
import gamelogic.Surface;

public class ControlPanel extends JPanel {
	JToolBar terrainToolBar;
	ElementDetailPanel<Surface> surfaceToolBar;
	ElementDetailPanel<Species> speciesToolBar;

	public ControlPanel() {
		setBackground(Color.red);
		setUpUI();
	}
	
	private void setUpUI() {
		setLayout(new BorderLayout());
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
		terrainToolBar = new JToolBar(null, JToolBar.VERTICAL);
		surfaceToolBar = new ElementDetailPanel<>("Surface");
		speciesToolBar = new ElementDetailPanel<>("Species");
		terrainToolBar.add(new JButton("Test"));
		toolBarPanel.add(terrainToolBar);
		toolBarPanel.add(surfaceToolBar);
		toolBarPanel.add(speciesToolBar);
		add(toolBarPanel, BorderLayout.WEST);
	}
	
	public void setSurfaces(ArrayList<Surface> surfaceArray) { surfaceToolBar.setElementArray(surfaceArray); }
	public void setSpecies(ArrayList<Species> speciesArray) { speciesToolBar.setElementArray(speciesArray); }
}
