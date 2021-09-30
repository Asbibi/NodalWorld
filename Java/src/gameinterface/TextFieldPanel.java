package gameinterface;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TextFieldPanel extends JPanel {
	JTextField textField;
	
	public TextFieldPanel(String fieldName) {
		setLayout(new BorderLayout());
		JLabel itemLabel = new JLabel(fieldName);
		itemLabel.setPreferredSize(new Dimension(50, 16));
		add(itemLabel, BorderLayout.WEST);
		textField = new JTextField(5);
		add(textField, BorderLayout.CENTER);
	}
	
	public String getFieldString() { return textField.getText(); }
	public void setFieldString(String text) { textField.setText(text); setFieldColor(ControlPanel.getStandardFieldColor()); }
	
	public void addactionListener(ActionListener action) { textField.addActionListener(action); }
	public void setFieldColor(Color color) { textField.setBackground(color); }
}
