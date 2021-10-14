package gameinterface;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import gamelogic.Element;
import gamelogic.Species;

/**
* Derived class to display all the properties of a species
* 
* @see ElementDetailPanel
* @see Species
*/
public class SpeciesDetailPanel extends ElementDetailPanel {

	private TextFieldPanel triggerTimeField;
	private TextFixedFieldPanel memberCount;
	
	
	public SpeciesDetailPanel() {
		super();
		triggerTimeField = new TextFieldPanel("Period");
		add(triggerTimeField);
		memberCount = new TextFixedFieldPanel("Count", true);
		memberCount.setButtonIcon(new ImageIcon("res/_System_DeleteCroce_Mini.png"));
		add(memberCount);
		addApplyButton();
	}
	
	@Override
	public void updateVariableElementInfo(Element e) {
		Species species = (Species)e;
		if (species != null)
			memberCount.setLabelString(Integer.toString(species.getMembers().size()));
	}
	
	@Override
	public void setElement(Element e) {
		super.setElement(e);
		setSpecies((Species)e);
	}

	
	/**
	* @param the species to display
	*/
	private void setSpecies(Species species) {
		if (species != null) {
			triggerTimeField.setFieldString(species.getTriggerTime().toString());
			memberCount.setLabelString(Integer.toString(species.getMembers().size()));
		} else {
			triggerTimeField.setFieldString("");
			memberCount.setLabelString("-");
		}
	}
	
	@Override
	public void applyModificationsToElement(Element e) {
		Species species = (Species)e;
		if (species == null)
			return;		

		try {
			species.setTriggerTime(Integer.parseInt(triggerTimeField.getFieldString()));
			super.applyModificationsToElement(e);
			triggerTimeField.setFieldColor(ControlPanel.getStandardFieldColor());
		} catch (Exception except) {
			triggerTimeField.setFieldColor(ControlPanel.getWrongFieldColor());		
		}
	}
	
	public void replaceMemberButtonActionListener(ActionListener l) { memberCount.removeAllButtonActionListener(); memberCount.addButtonActionListener(l); }
}
