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
	
	/**
	* @param fieldName the name of the property (which is a String)
	*/
	public TextFixedFieldPanel(String fieldName) {
		this(fieldName, false);
	}
	/**
	* @param fieldName the name of the property (which is a String)
	* @param withButton indicated if the property field should have a button at its right side	
	*/
	public TextFixedFieldPanel(String fieldName, boolean withButton) {
		super(fieldName, withButton);
		textLabel = new JLabel("-", SwingConstants.CENTER);
		add(textLabel, BorderLayout.CENTER);
	}
	

	/**
	* @param text the new value of the property as a String
	*/
	public void setLabelString(String text) { textLabel.setText(text); }
}
