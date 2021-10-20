package gameinterface;

import java.awt.Dimension;

import javax.swing.*;

import gameinterface.components.TerrainVisualizerComponent;
import gameinterface.components.TerrainVisualizerVectors;
import gamelogic.Terrain;

/**
* Complete visualizer of the terrainSlots of a Terrain.<br/>
* It display the visualization of the slots (using a TerrainStackVisualizer) as well as buttons to change the focused slot, and buttons to add/move/remove the slots.
* 
* @see TerrainVisualizerComponent
* @see TerrainVisualizerVectors
*/
public class TerrainVisualizerPanel extends JPanel {
	private TerrainVisualizerComponent visualizer;
	private JScrollPane visuScrollPanel;
	
	private TerrainVisualizerVectors visuVectors;
	private JButton focusUp;
	private JButton focusDown;
	private JLabel currentFocusLabel;
	private JToggleButton focusColor;
	
	private JButton upButton;
	private JButton downButton;
	private JButton removeButton;
	private JButton addButton;
	

	/**
	* @param terrain the terrain represented
	*/
	TerrainVisualizerPanel(Terrain terrain) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		visualizer = new TerrainVisualizerComponent(terrain);
		visuScrollPanel = new JScrollPane(visualizer) { @Override public Dimension getPreferredSize() { return visualizer.getParentDimension(); } };
		visuScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(visuScrollPanel);

		Dimension buttonDimension = new Dimension(25,25);
		JPanel focusButtonsPanel = new JPanel();
		ImageIcon upFocusIcon = new ImageIcon("res/_System_FocusUpArrow.png");
		ImageIcon downFocusIcon = new ImageIcon("res/_System_FocusDownArrow.png");
		ImageIcon eyeIcon = new ImageIcon("res/_System_Eye.png");
		focusUp = new JButton(upFocusIcon);
		focusDown = new JButton(downFocusIcon);
		currentFocusLabel = new JLabel();
		updateLayerLabelText();
		focusColor = new JToggleButton(eyeIcon);
		focusUp.setPreferredSize(buttonDimension);
		focusDown.setPreferredSize(buttonDimension);
		focusColor.setPreferredSize(buttonDimension);
		focusUp.addActionListener( e -> {
			visualizer.focusPreviousLayer();
			updateLayerLabelText();
			} );
		focusDown.addActionListener( e -> {
			visualizer.focusNextLayer();
			updateLayerLabelText();
			} );
		focusColor.addActionListener( e -> {
			visualizer.flipOnlyFocusedInColor();
			updateLayerLabelText();
			} );
		visuVectors = new TerrainVisualizerVectors(visualizer);		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);		
		separator.setForeground(GameFrame.getSeparatorColor());
		focusButtonsPanel.add(visuVectors);
		focusButtonsPanel.add(separator);
		focusButtonsPanel.add(focusUp);
		focusButtonsPanel.add(focusDown);
		focusButtonsPanel.add(currentFocusLabel);
		focusButtonsPanel.add(focusColor);
		add(focusButtonsPanel);	
		
		JPanel manageButtonsPanel = new JPanel();
		ImageIcon addIcon = new ImageIcon("res/_System_Add.png");
		ImageIcon upIcon = new ImageIcon("res/_System_UpArrow.png");
		ImageIcon downIcon = new ImageIcon("res/_System_DownArrow.png");
		ImageIcon delIcon = new ImageIcon("res/_System_DeleteCroce.png");
		addButton = new JButton(addIcon);
		upButton = new JButton(upIcon);
		downButton = new JButton(downIcon);
		removeButton = new JButton(delIcon);
		addButton.setPreferredSize(buttonDimension);
		upButton.setPreferredSize(buttonDimension);
		downButton.setPreferredSize(buttonDimension);
		removeButton.setPreferredSize(buttonDimension);
		addButton.addActionListener( e -> addTerrainSlot() );
		upButton.addActionListener( e -> moveUpTerrainSlot() );
		downButton.addActionListener( e -> moveDownTerrainSlot() );
		removeButton.addActionListener( e -> removeTerrainSlot() );
		manageButtonsPanel.add(addButton);
		manageButtonsPanel.add(upButton);
		manageButtonsPanel.add(downButton);
		manageButtonsPanel.add(removeButton);
		add(manageButtonsPanel);
	}
	
	/**
	* @param terrain the terrain represented
	*/
	public void setTerrain(Terrain terrain) {
		visualizer.setTerrain(terrain);
		revalidate();
		repaint();
	}
	
	/**
	* Updates the index of the focused layer displayed in the JLabel to fit the actual focused layer index.
	*/
	private void updateLayerLabelText() {
		currentFocusLabel.setText(Integer.toString(visualizer.getFocusedLayer()));
	}

	/**
	* Adds a new empty slot above the focused slot.
	*/
	private void addTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		visualizer.getTerrain().addSlot(focused);
		visualizer.revalidate();
		repaint();
	}
	/**
	* Move up the focused slot.
	*/
	private void moveUpTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		visualizer.getTerrain().swapSlots(focused, focused-1);
		visualizer.focusPreviousLayer();
	}
	/**
	* Move down the focused slot.
	*/
	private void moveDownTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		visualizer.getTerrain().swapSlots(focused, focused+1);
		visualizer.focusNextLayer();
	}
	/**
	* Remove the focused slot.
	*/
	private void removeTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		if (focused < 0)
			return;
        
    	visualizer.getTerrain().removeSlot(focused);
    	if (focused != 0)
    		visualizer.focusPreviousLayer();
    	else {
    		visualizer.revalidate();
    		repaint();    		
    	}
	}
}
