package gameinterface.nodaleditor;

import gamelogic.GameManager;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Surface;
import gamelogic.NetworkIOException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.awt.event.ActionEvent;

import java.awt.Component;
import java.awt.Dimension;

/**
* Info panel of a node, allows the user to manually set values in unconnected inputs that allow it.
* 
* @see Input
*/ 
public class NodeInfoPanel extends JPanel implements Serializable {

	/**
	* @param game 
	* @param node
	*/ 
	public NodeInfoPanel(GameManager game, Node node) {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(leftJustify(new JLabel(node.toString())));
		add(Box.createVerticalStrut(20));

		for(Input input : node.getInputs()) {
			add(leftJustify(buildInputPanel(game, input)));
		}
	}

	private JPanel buildInputPanel(GameManager game, Input input) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.add(new JLabel(input.toString()));
		panel.add(Box.createHorizontalStrut(10));

		if(input.isManual()) {
			try {
				if(input.getDataClass().equals(Boolean.class)) {
					JCheckBox checkBox = new JCheckBox();
					checkBox.setSelected(input.getManualValue(Boolean.class));
					checkBox.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							input.setManualValue(checkBox.isSelected());
						}
					});
					panel.add(checkBox);

				} else if(input.getDataClass().equals(Integer.class)) {
					JSpinner spinner = new JSpinner(new SpinnerNumberModel((int) input.getManualValue(Integer.class), (int) Integer.MIN_VALUE, (int) Integer.MAX_VALUE, 1));
					spinner.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							input.setManualValue((Integer) (spinner.getModel().getValue()));
						}
					});
					spinner.setMaximumSize(new Dimension(200, 25));
					panel.add(spinner);

				} else if(input.getDataClass().equals(Double.class)) {
					JSpinner spinner = new JSpinner(new SpinnerNumberModel((double) input.getManualValue(Double.class), (double) -Double.MAX_VALUE, (double) Double.MAX_VALUE, 0.1));
					spinner.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							input.setManualValue((Double) (spinner.getModel().getValue()));
						}
					});
					spinner.setMaximumSize(new Dimension(200, 25));
					panel.add(spinner);

				} else if(input.getDataClass().equals(Surface.class)) {
					JComboBox comboBox = new JComboBox<Surface>();
					for(Surface surface : game.getSurfaceArray()) 
						comboBox.addItem(surface);
					comboBox.setSelectedItem(input.getManualValue(Surface.class));
					comboBox.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							input.setManualValue((Surface) (comboBox.getSelectedItem()));
						}
					});
					comboBox.setMaximumSize(new Dimension(200, 25));
					panel.add(comboBox);

					game.addSurfaceListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							comboBox.removeAllItems();
							for(Surface surface : game.getSurfaceArray()) 
								comboBox.addItem(surface);
							try { comboBox.setSelectedItem(input.getManualValue(Surface.class)); }
							catch(NetworkIOException exception) { exception.printStackTrace(); }
						}
					});

				}
			} catch(NetworkIOException exception) {
				exception.printStackTrace();
			}
		}

		return panel;
	}

	private JComponent leftJustify(JComponent panel)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(panel);
	    b.add(Box.createHorizontalGlue());
	    return b;
	}

}
