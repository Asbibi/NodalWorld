package gameinterface;

import java.awt.*;
import javax.swing.*;

public class TextFullPanel extends JPanel {
	JLabel textLabel;
	
	public TextFullPanel(String fieldName) {
		setLayout(new BorderLayout());
		JLabel itemLabel = new JLabel(fieldName);
		itemLabel.setPreferredSize(new Dimension(50, 16));
		add(itemLabel, BorderLayout.WEST);
		textLabel = new JLabel("-", SwingConstants.CENTER);
		add(textLabel, BorderLayout.CENTER);
	}
	
	public void setLabelString(String text) { textLabel.setText(text); }

}
