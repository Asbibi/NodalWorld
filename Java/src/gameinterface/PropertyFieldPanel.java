package gameinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;

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

	/**
	* @param the property name, displayed on the left side of the field with a fixed size
	*/ 
	public PropertyFieldPanel(String fieldName) {
		setLayout(new BorderLayout());
		JLabel itemLabel = new JLabel(fieldName);
		itemLabel.setPreferredSize(new Dimension(50, 16));
		add(itemLabel, BorderLayout.WEST);
	}
}
