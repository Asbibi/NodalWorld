package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.Input;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.Component;

/**
* Info panel of a node, allows the user to manually set values in unconnected inputs that allow it.
* 
* @see Input
*/ 
public class NodeInfoPanel extends JPanel {

	/**
	* @param node
	*/ 
	public NodeInfoPanel(Node node) {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		add(new JLabel(node.toString()));

		for(Input input : node.getInputs()) {
			add(buildInputPanel(input));
		}
	}

	private JPanel buildInputPanel(Input input) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.add(new JLabel(input.toString()));

		if(input.isManual()) {
			if(input.getDataClass().equals(Integer.class)) {
				JSpinner spinner = new JSpinner(new SpinnerNumberModel((int) input.getManualValue(Integer.class), (int) Integer.MIN_VALUE, (int) Integer.MAX_VALUE, 1));
				spinner.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						input.setManualValue((Integer) (spinner.getModel().getValue()));
					}
				});
				panel.add(spinner);
			}
		}

		return panel;
	}

}
