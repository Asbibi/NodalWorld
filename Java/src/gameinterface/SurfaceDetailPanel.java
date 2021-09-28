package gameinterface;

import gamelogic.Element;
import gamelogic.Surface;

public class SurfaceDetailPanel extends ElementDetailPanel {

	public SurfaceDetailPanel() {
		super();
		addApplyButton();
	}
	
	@Override
	public void setElement(Element e) {
		super.setElement(e);
		setSpecies((Surface)e);
	}
	
	private void setSpecies(Surface species) {
		// stuff
		System.out.println("Surface set");
	}

}
