package gameinterface;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import gameinterface.components.TerrainStackVisualizer;
import gameinterface.components.TerrainStackVisualizerVectors;
import gamelogic.TerrainLayer;

/**
* A property field for text that can be edited.
* 
* @see PropertyFieldPanel
*/
public class TextFieldPanel extends PropertyFieldPanel {
	JTextField textField;
	
	public TextFieldPanel(String fieldName) {
		super(fieldName);
		textField = new JTextField(5);
		add(textField, BorderLayout.CENTER);
	}
	
	/**
	* @return the property value as a string
	*/
	public String getFieldString() { return textField.getText(); }
	/**
	* @param the new value of the property as a string
	*/
	public void setFieldString(String text) { textField.setText(text); setFieldColor(ControlPanel.getStandardFieldColor()); }
	
	/**
	* @param the ActionListener to add to the inputField
	*/
	public void addActionListener(ActionListener action) { textField.addActionListener(action); }
	/**
	* @param the color to set to the inputField background
	*/
	public void setFieldColor(Color color) { textField.setBackground(color); }
}
