package gameinterface;

import java.awt.Color;

import javax.swing.JPanel;

import gamelogic.Element;
import gamelogic.Species;

public class SpeciesDetailPanel extends ElementDetailPanel {

	private TextFieldPanel triggerTimeField;
	private TextFullPanel memberCount;
	
	
	public SpeciesDetailPanel() {
		super();
		triggerTimeField = new TextFieldPanel("Period");
		add(triggerTimeField);
		memberCount = new TextFullPanel("Count");
		add(memberCount);
		addApplyButton();
	}
	
	
	@Override
	public void setElement(Element e) {
		super.setElement(e);
		setSpecies((Species)e);
	}
	
	private void setSpecies(Species species) {
		if (species != null) {
			triggerTimeField.setFieldString(species.getTriggerTime().toString());
			memberCount.setLabelString(Integer.toString(species.getMembers().size()));
			System.out.println("Species set");
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
}
