package gameinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
* The property field class is a panel where a property is represented among sied a JLabel that displays the property name
* This class should be used as it but should be derived depending on the property type you want.
* For each derived class, remeber to add a field/button to display and interact with your property value, as this class only displays its name. 
* 
* @see ColorFieldPanel
* @see TextFieldPanel
* @see TextFixedFieldPanel
*/
public class PropertyFieldPanel extends JPanel {
	private JButton propertyButton;
	
	/**
	* @param the property name, displayed on the left side of the field with a fixed size
	* @param withButton indicates if there should be a button
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
	
	public void setButtonIcon(ImageIcon buttonIcon) {
		if (propertyButton == null)
			return;
		
		propertyButton.setIcon(buttonIcon);
	}
	
	public void removeAllButtonActionListener() { for (ActionListener l : propertyButton.getActionListeners())		propertyButton.removeActionListener(l); }
	public void addButtonActionListener(ActionListener l) { propertyButton.addActionListener(l); }
}
