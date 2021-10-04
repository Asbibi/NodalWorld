package gameinterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import gameinterface.components.ImageComponent;
import gamelogic.Element;

/**
* This class is used to display the details of an element.
* In practice it's used as a part of the ElementManagerToolBar.
* It's mainly composed of property fields panel
* 
* Don't directly use this class, but instead use the surface or species derivated ones as they have additionnal fields specific to those element subclasses.
* 
* @see ElementManagerToolBar
* @see SurfaceDetailPanel
* @see SpeciesDetailPanel
* @see PropertyFieldPanel
*/ 
public class ElementDetailPanel extends JPanel {
	protected TextFieldPanel nameField;
	protected ImageComponent imageField;
	protected JButton applyButton;

	public ElementDetailPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		nameField = new TextFieldPanel("Name");
		add(nameField);
		imageField = new ImageComponent();
		add(imageField);
	}
	
	/**
	* Add the "Apply" button to the panel.
	* It's a separated method as it needs to be called at the END of the derivated classes constructor.
	* That way it will be displayed at the end of the panel.
	*/
	protected void addApplyButton() {
		applyButton = new JButton("Apply");		
		add(applyButton);
	}
	
	/**
	* @param the ActionListener to adds to the "Apply" button.
	*/ 
	public void addApplyListener(ActionListener applyActionListener) {
		if (applyButton!= null)
			applyButton.addActionListener(applyActionListener);
	}
	
	/**
	* Override this method in the derivated classes to update the specific property fields it have 
	* @param the element instance that should be represented by the panel
	*/ 
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
	
	/**
	* @param the element instance that should receive the new values of the property fields.
	*/
	public void applyModificationsToElement(Element e) {
		if (e == null)
			return;		

		e.setName(nameField.getFieldString());
		e.setImage(imageField.getImage());
	}
}
