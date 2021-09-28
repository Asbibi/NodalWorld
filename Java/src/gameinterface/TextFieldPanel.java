package gameinterface;

import java.awt.*;
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
		//System.out.println("lab" + itemLabel.getPreferredSize());
	}
	
	public String getFieldString() { return textField.getText(); }
	public void setFieldString(String text) { textField.setText(text); }
}
