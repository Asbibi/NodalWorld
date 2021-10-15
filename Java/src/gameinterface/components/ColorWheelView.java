package gameinterface.components;

import java.awt.*;

/**
* This class is the view of a ColorWheelComponent.
* 
* @see ColorWheelComponent
*/ 
public class ColorWheelView {
	
	/**
	* @param graphics2D the Graphic Context to use to display the color wheel
	* @param component the color wheel model/controller to display
	*/ 
	public void paint(Graphics2D g, ColorWheelComponent owner) {
		int sliderSupp = 5 + owner.getSliderWidth();
		int height = owner.getHeight();
		int xOffset = owner.getXOffset();
		int xCursor = owner.getCursorX();
		int yCursor = owner.getCursorY();
		Color cursorColor = owner.getValue() > 0.5 ? Color.black : Color.white;
		
		// color wheel and value slider display
		g.drawImage(owner.getWheelImage(), xOffset, 0, height + sliderSupp, height, null);
		
		// cursors display
		g.setColor(cursorColor);
		g.drawRect(xOffset + xCursor-2, yCursor-2, 5, 5);
		g.drawRect(xOffset + height + 4, owner.getValueCursorY()-2, sliderSupp - 3, 5);
		
		// sample color display
		int xSample = xOffset + xCursor + (xCursor < height - 35 ? 4 : -38);
		int ySample = yCursor + (yCursor < 40 ? 4 : -38) ;
		g.setColor(owner.getColorFromCursor());
		g.fillRect(xSample, ySample, 35, 35);
		g.setColor(cursorColor);
		g.drawRect(xSample, ySample, 35, 35);		
	}	
}
