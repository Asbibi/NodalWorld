package gameinterface;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import gameinterface.components.TerrainStackVisualizer;
import gameinterface.components.TerrainStackVisualizerVectors;
import gamelogic.TerrainLayer;

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
	

	TerrainVisualizerPanel(List<TerrainLayer> stack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		visualizer = new TerrainStackVisualizer(stack);
		visuScrollPanel = new JScrollPane(visualizer) { @Override public Dimension getPreferredSize() { return visualizer.getParentDimension(); } };
		visuScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(visuScrollPanel);

		Dimension buttonDimension = new Dimension(25,25);
		JPanel buttonPanel = new JPanel();
		ImageIcon upIcon = new ImageIcon("res/_System_UpArrow.png");
		ImageIcon downIcon = new ImageIcon("res/_System_DownArrow.png");
		ImageIcon eyeIcon = new ImageIcon("res/_System_Eye.png");
		focusUp = new JButton(upIcon);
		focusDown = new JButton(downIcon);
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
		buttonPanel.add(visuVectors);
		buttonPanel.add(separator);
		buttonPanel.add(focusUp);
		buttonPanel.add(focusDown);
		buttonPanel.add(currentFocusLabel);
		buttonPanel.add(focusColor);
		add(buttonPanel);		
	}
	
	/**
	* Update the index of the focused layer displayed to the actual focused layer index
	*/
	private void updateLayerLabelText() {
		currentFocusLabel.setText(Integer.toString(visualizer.getFocusedLayer()));
	}
}
