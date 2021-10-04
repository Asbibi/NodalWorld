package gameinterface;

import java.awt.*;
import javax.swing.*;

/**
* A property field for a NON editable text.
* 
* @see PropertyFieldPanel
*/
public class TextFixedFieldPanel extends PropertyFieldPanel {
	JLabel textLabel;
	
	public TextFixedFieldPanel(String fieldName) {
		super(fieldName);
		textLabel = new JLabel("-", SwingConstants.CENTER);
		add(textLabel, BorderLayout.CENTER);
	}
	

	/**
	* @param the new value of the property as a string
	*/
	public void setLabelString(String text) { textLabel.setText(text); }
}
