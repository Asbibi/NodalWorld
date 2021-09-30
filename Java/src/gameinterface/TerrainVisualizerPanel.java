package gameinterface;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import gameinterface.components.TerrainStackVisualizer;
import gamelogic.TerrainLayer;

public class TerrainVisualizerPanel extends JPanel {
	private TerrainStackVisualizer visualizer;
	private JButton focusUp;
	private JButton focusDown;
	private JLabel currentFocusLabel;
	private JToggleButton focusColor;
	

	TerrainVisualizerPanel(List<TerrainLayer> stack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		visualizer = new TerrainStackVisualizer(stack);
		JScrollPane visuScrollPanel = new JScrollPane(visualizer);
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
		focusUp.addActionListener( e -> {visualizer.focusPreviousLayer();updateLayerLabelText();} );
		focusDown.addActionListener( e -> {visualizer.focusNextLayer();updateLayerLabelText();} );
		focusColor.addActionListener( e -> {visualizer.flipOnlyFocusedInColor();updateLayerLabelText();} );
		buttonPanel.add(focusUp);
		buttonPanel.add(focusDown);
		buttonPanel.add(currentFocusLabel);
		buttonPanel.add(focusColor);
		add(buttonPanel);		
	}
	
	private void updateLayerLabelText() {
		currentFocusLabel.setText(Integer.toString(visualizer.getFocusedLayer()));
	}
}
