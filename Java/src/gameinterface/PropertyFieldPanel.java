package gameinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
* The property field class is a panel where a property is represented by a JLabel that displays the property name and a component that depends on the property.<br/>
* This class should be derived depending on the property type you want.<br/>
* For each derived class, remeber to add a field/button to display and interact with your property value, as this class only displays its name. 
* 
* @see ColorFieldPanel
* @see TextFieldPanel
* @see TextFixedFieldPanel
*/
public abstract class PropertyFieldPanel extends JPanel {
	private JButton propertyButton;
	
	/**
	* @param the property name, displayed on the left side of the field with a fixed size
	* @param withButton indicates if there should be a button on the right end of the field
	*/
	public PropertyFieldPanel(String fieldName, boolean withButton) {
		setLayout(new BorderLayout());
		JLabel itemLabel = new JLabel(fieldName);
		itemLabel.setPreferredSize(new Dimension(50, 16));
		add(itemLabel, BorderLayout.WEST);
		if (withButton) {
			propertyButton = new JButton();
			propertyButton.setPreferredSize(new Dimension(16,16));
			add(propertyButton, BorderLayout.EAST);		
		}
		else
			propertyButton = null;
	}
	
	/**
	* If the button is not null, sets its ImageIcon.
	* @param withButton indicates if there should be a button
	*/
	public void setButtonIcon(ImageIcon buttonIcon) {
		if (propertyButton == null)
			return;
		
		propertyButton.setIcon(buttonIcon);
	}
	/**
	* If the button is not null, remove all its ActionListeners.
	*/
	public void removeAllButtonActionListener() {
		if (propertyButton == null)
			return;
		
		for (ActionListener l : propertyButton.getActionListeners())
				propertyButton.removeActionListener(l);
	}
	/**
	* If the button is not null, add the listener to its ActionListeners.
	* @param actionListener the ActionListener to add
	*/
	public void addButtonActionListener(ActionListener l) {
		if (propertyButton == null)
			return;
		propertyButton.addActionListener(l);
	}
}
