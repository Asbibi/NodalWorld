package gameinterface.components;

import java.awt.Graphics2D;
import java.awt.Image;

public class ImageView {

	public void paint(Graphics2D g, ImageComponent owner) {
		Image image = owner.getImage();
		if (image == null || image.getWidth(null) < 1) {
			g.setColor(ImageComponent.getNullColor());
	        g.fillRoundRect(0, 0, owner.getWidth(), owner.getHeight(), 0, 0);
		} else {
	        g.drawImage(owner.getImage(), 0, 0, owner.getWidth(), owner.getHeight(), owner);
		}
	}
}
