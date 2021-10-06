package gameinterface;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import gameinterface.components.TerrainStackVisualizer;
import gameinterface.components.TerrainStackVisualizerVectors;
import gamelogic.Terrain;

/**
* Complete visualizer of the layers of a terrainStack.
* It display the layers as well as buttons to change the focused layer.
* 
* @see TerrainLayer
* @see TerrainStackVisualizer
* @see TerrainStackVisualizerVectors
*/
public class TerrainVisualizerPanel extends JPanel {
	private TerrainStackVisualizer visualizer;
	private JScrollPane visuScrollPanel;
	
	private TerrainStackVisualizerVectors visuVectors;
	private JButton focusUp;
	private JButton focusDown;
	private JLabel currentFocusLabel;
	private JToggleButton focusColor;
	
	private JButton upButton;
	private JButton downButton;
	private JButton removeButton;
	private JButton addButton;
	

	TerrainVisualizerPanel(Terrain terrain) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		visualizer = new TerrainStackVisualizer(terrain);
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
		visuVectors = new TerrainStackVisualizerVectors(visualizer);		
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
	* Update the index of the focused layer displayed to the actual focused layer index
	*/
	private void updateLayerLabelText() {
		currentFocusLabel.setText(Integer.toString(visualizer.getFocusedLayer()));
	}

	
	private void addTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		visualizer.getTerrain().addSlot(focused);
		visualizer.revalidate();
		repaint();
	}
	private void moveUpTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		visualizer.getTerrain().swapSlots(focused, focused-1);
		visualizer.focusPreviousLayer();
	}
	private void moveDownTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		visualizer.getTerrain().swapSlots(focused, focused+1);
		visualizer.focusNextLayer();
	}
	private void removeTerrainSlot() {
		int focused = visualizer.getFocusedLayer();
		if (focused < 0)
			return;
		int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete the Slot " + focused +" ? It can't be undone.", "Delete", JOptionPane.YES_NO_OPTION);
        if (reply != JOptionPane.YES_OPTION)
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
