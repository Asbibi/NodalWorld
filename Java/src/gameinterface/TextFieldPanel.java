package gameinterface;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextFieldPanel extends JPanel {
	JTextField textField;
	
	public TextFieldPanel(String fieldName) {
		setLayout(new BorderLayout());
		add(new JLabel(fieldName), BorderLayout.WEST);
		textField = new JTextField(5);
		add(textField, BorderLayout.CENTER);
	}
	
	public String getFieldString() { return textField.getText(); }
	public void setFieldString(String text) { textField.setText(text); }
}
