package gameinterface;

import java.awt.BorderLayout;
import java.awt.Color;

import gameinterface.components.ColorButton;

/**
* A property field for Color.
* 
* @see ColorButton
* @see PropertyFieldPanel
*/ 
public class ColorFieldPanel extends PropertyFieldPanel {
	ColorButton colorField;

	/**
	* @param fieldName the name of the property (which is a Color)
	*/ 
	public ColorFieldPanel(String fieldName) { 
		this(fieldName, false);
	}
	/**
	* @param fieldName the name of the property (which is a Color)
	* @param withButton indicated if the property field should have a button at its right side	
	*/ 
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
