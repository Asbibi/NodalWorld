package gameinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import gameinterface.components.ColorButton;
import gameinterface.components.ColorWheelComponent;

/**
* A property field for Color.
* 
* @see ColorButton
* @see PropertyFieldPanel
*/ 
public class ColorFieldPanel extends PropertyFieldPanel {
	ColorButton colorField;

	public ColorFieldPanel(String fieldName) { 
		this(fieldName, false);
	}
	public ColorFieldPanel(String fieldName, boolean withButton) {
		super(fieldName, withButton);
		colorField = new ColorButton(Color.LIGHT_GRAY);
		add(colorField, BorderLayout.CENTER);
	}
	
	
	/**
	* @return the color value of the field
	*/ 
	public Color getFieldColor() { return colorField.getColor(); }
	/**
	* @param the value of color the field must have
	*/ 
	public void setFieldColor(Color color) { colorField.setColor(color); }
}
