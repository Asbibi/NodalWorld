package gameinterface;

import gamelogic.Element;
import gamelogic.Species;

public class SpeciesDetailPanel extends ElementDetailPanel {

	@Override
	public void setElement(Element e) {
		super.setElement(e);
		setSpecies((Species)e);
	}
	
	private void setSpecies(Species species) {
		// stuff
		System.out.println("Species set");
	}
}
