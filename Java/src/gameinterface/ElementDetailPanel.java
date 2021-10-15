package gameinterface;

import java.awt.event.ActionListener;

import javax.swing.*;

import gameinterface.components.ImageComponent;
import gamelogic.Element;

/**
* This class is used to display the details of an element.<br/>
* In practice it's used as a part of the ElementManagerToolBar.<br/>
* It's mainly composed of property fields panel.<br/><br/>
* 
* Don't directly use this class, but instead use the surface or species derivated ones as they have additionnal fields specific to those element subclasses.<br/>
* 
* @see ElementManagerToolBar
* @see SurfaceDetailPanel
* @see SpeciesDetailPanel
* @see PropertyFieldPanel
*/ 
public abstract class ElementDetailPanel extends JPanel {
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
	* This method should be override so at each call, every property field that is purely display (not interactable) should updates itself to the current element value.
	* @param element the element to update the display only fields on
	*/
	public void updateVariableElementInfo(Element e) {}
	
	/**
	* Add the "Apply" button to the panel.<br/>
	* It's a separated method as it needs to be called at the END of the derivated classes constructor.<br/>
	* That way it will be displayed at the end of the panel.
	*/
	protected void addApplyButton() {
		applyButton = new JButton("Apply");		
		add(applyButton);
	}
	
	/**
	* @param applyActionListener the ActionListener to adds to the "Apply" button.
	*/ 
	public void addApplyListener(ActionListener applyActionListener) {
		if (applyButton!= null)
			applyButton.addActionListener(applyActionListener);
	}
	
	/**
	* Override this method in the derivated classes to update the specific property fields it has.
	* @param element the element instance that should be represented by the panel
	*/ 
	public void setElement(Element e) {
		if (e!= null) {
			nameField.setFieldString(e.toString());
			imageField.setImage(e.getImageFile());	
			imageField.setIsEnabled(true);
		} else {
			nameField.setFieldString("");
			imageField.setImage(null);
			imageField.setIsEnabled(false);
		}
	}
	
	/**
	* Applies all modifications the user made on the different editable property fields on the given element. 
	* @param element the element instance that should receive the modified values of the editable property fields.
	*/
	public void applyModificationsToElement(Element e) {
		if (e == null)
			return;		

		e.setName(nameField.getFieldString());
		e.setImageFile(imageField.getImageFile());
	}
}
