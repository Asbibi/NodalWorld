package gameinterface;

import java.awt.Color;

import gamelogic.Element;
import gamelogic.Surface;

/**
* Derived class to display all the properties of a surface.
* 
* @see ElementDetailPanel
* @see Surface
*/
public class SurfaceDetailPanel extends ElementDetailPanel {
	
	private ColorFieldPanel colorField;

	public SurfaceDetailPanel() {
		super();
		colorField = new ColorFieldPanel("Color");
		add(colorField);
		addApplyButton();
	}
	
	@Override
	public void setElement(Element e) {
		super.setElement(e);
		Surface surface = (Surface)e;
		
		if (surface != null && surface != Surface.getEmpty()) {
			colorField.setFieldColor(surface.getColor());
		} else {
			colorField.setFieldColor(new Color(0,0,0,0));
		}
	}
	
	
	@Override
	public void applyModificationsToElement(Element e) {
		super.applyModificationsToElement(e);
		Surface surface = (Surface)e;
		if (surface == null || surface == Surface.getEmpty())
			return;

		surface.setColor(colorField.getFieldColor());
	}
}
