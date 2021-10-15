package gameinterface;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
* A property field for text that can be edited.
* 
* @see PropertyFieldPanel
*/
public class TextFieldPanel extends PropertyFieldPanel {
	JTextField textField;
	
	/**
	* @param fieldName the name of the property (which is an editable String)
	*/
	public TextFieldPanel(String fieldName) { 
		this(fieldName, false);
	}
	/**
	* @param fieldName the name of the property (which is an editable String)
	* @param withButton indicated if the property field should have a button at its right side	
	*/
	public TextFieldPanel(String fieldName, boolean withButton) {
		super(fieldName, withButton);
		textField = new JTextField(5);
		add(textField, BorderLayout.CENTER);
	}
	
	/**
	* @return the property value as a String
	*/
	public String getFieldString() { return textField.getText(); }
	/**
	* @param text the new value of the property as a String
	*/
	public void setFieldString(String text) { textField.setText(text); setFieldColor(GameFrame.getStandardFieldColor()); }
	
	/**
	* @param action the ActionListener to add to the inputField
	*/
	public void addActionListener(ActionListener action) { textField.addActionListener(action); }
	/**
	* @param color the color to set to the inputField background
	*/
	public void setFieldColor(Color color) { textField.setBackground(color); }
	
	/**
	* @param enable indicates if the input field will be enable or not
	*/
	public void setEnable(boolean enable) {
		textField.setEnabled(enable);
		setFieldColor(enable ? GameFrame.getStandardFieldColor() : Color.lightGray);
	}
}
