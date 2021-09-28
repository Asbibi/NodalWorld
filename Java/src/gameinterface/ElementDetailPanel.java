package gameinterface;

import java.awt.FlowLayout;

import javax.swing.*;

import gamelogic.Element;

public class ElementDetailPanel extends JPanel {
	protected TextFieldPanel nameField;
	protected ImageComponent imageField;

	public ElementDetailPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		nameField = new TextFieldPanel("Name");
		add(nameField);
		imageField = new ImageComponent();
		add(imageField);
	}
	
	public void setElement(Element e) {
		nameField.setFieldString(e.toString());
		imageField.setImage(e.getImage());
	}
}
