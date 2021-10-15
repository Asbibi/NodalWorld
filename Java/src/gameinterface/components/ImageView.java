package gameinterface.components;

import java.awt.Graphics2D;
import java.awt.Image;

/**
* The view of a ImageCompoent.
* 
* @see ImageComponent
*/ 
public class ImageView {

	/**
	* @param graphics2D the Graphic Context to use to display the color wheel
	* @param component the color wheel model/controller to display
	*/ 
	public void paint(Graphics2D g, ImageComponent owner) {
		Image image = owner.getImage();
		if (image == null || image.getWidth(null) < 1) {
			g.setColor(ImageComponent.getNullColor());
	        g.fillRoundRect(0, 0, owner.getWidth(), owner.getHeight(), 0, 0);
		} else {
	        g.drawImage(image, 0, 0, owner.getWidth(), owner.getHeight(), owner);
		}
	}
}
