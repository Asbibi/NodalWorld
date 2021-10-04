package gameinterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import gameinterface.components.ImageComponent;
import gamelogic.Element;

public class ElementDetailPanel extends JPanel {
	protected TextFieldPanel nameField;
	protected ImageComponent imageField;
	protected JButton applyButton;

	public ElementDetailPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		nameField = new TextFieldPanel("Name");
		add(nameField);
		imageField = new ImageComponent();
		/*JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.add(imageField, BorderLayout.CENTER);*/
		add(imageField);
	}
	
	protected void addApplyButton() {
		applyButton = new JButton("Apply");
		//applyButton.setPreferredSize(new Dimension());		
		add(applyButton);
	}
	public void addApplyListener(ActionListener applyActionListener) {
		if (applyButton!= null)
			applyButton.addActionListener(applyActionListener);
	}
	
	public void setElement(Element e) {
		if (e!= null) {
			nameField.setFieldString(e.toString());
			imageField.setImage(e.getImage());	
			imageField.setIsEnabled(true);
		} else {
			nameField.setFieldString("");
			imageField.setImage(null);
			imageField.setIsEnabled(false);
		}
	}
	
	public void applyModificationsToElement(Element e) {
		if (e == null)
			return;		

		e.setName(nameField.getFieldString());
		e.setImage(imageField.getImage());
	}
}
