package gameinterface.components;

import java.awt.Graphics2D;
import java.awt.Image;

/**
* The model of the clickable image display
* 
* @see ImageComponent
*/ 
public class ImageView {

	/**
	* @param the Graphic Context to use to display the image
	* @param the image component
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
