package gameinterface.components;

import java.awt.*;

public class ColorWheelView {
	
	public void paint(Graphics2D g, ColorWheelComponent owner) {
		int sliderSupp = 5 + owner.getSliderWidth();
		int height = owner.getHeight();
		int xOffset = owner.getXOffset();
		g.setColor(owner.getColorFromCursor());
		g.fillRect(0, 0, 20, 20);
		g.drawImage(owner.getWheelImage(), xOffset, 0, height + sliderSupp, height, null);
		
		g.setColor(owner.getValue() > 0.5 ? Color.black : Color.white);
		g.drawRect(xOffset + owner.getCursorX()-2, owner.getCursorY()-2, 5, 5);
		g.drawRect(xOffset + height + 4, owner.getValueCursorY()-2, sliderSupp - 3, 5);
	}	
}
