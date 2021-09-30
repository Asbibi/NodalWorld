package gameinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import gameinterface.components.ColorButton;

public class ColorFieldPanel extends JPanel {
	ColorButton colorField;
	
	public ColorFieldPanel(String fieldName) {
		setLayout(new BorderLayout());
		JLabel itemLabel = new JLabel(fieldName);
		itemLabel.setPreferredSize(new Dimension(50, 16));
		add(itemLabel, BorderLayout.WEST);
		colorField = new ColorButton(Color.LIGHT_GRAY);
		add(colorField, BorderLayout.CENTER);
	}
	
	public Color getFieldColor() { return colorField.getColor(); }
	public void setFieldColor(Color color) { colorField.setColor(color); }
}
