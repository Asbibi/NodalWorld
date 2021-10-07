package gamelogic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFile {
	private String path;
	//private Image image;
	private BufferedImage image;
	
	
	public ImageFile(String pathFile) {	
		try {
			File fileImage = new File(pathFile);
			image = ImageIO.read(fileImage);
			path = fileImage.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ImageFile(BufferedImage image) {	
		this.image = image;
		path = null;
	}
	
	public String getPath() { return path; }
	public Image getImage() { return image; }
	
	/**
	 * @return The image with its BufferedImage type
	 */
	public BufferedImage getBufferedImage() { return image; }
}
