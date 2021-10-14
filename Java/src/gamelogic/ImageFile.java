package gamelogic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* Utility class used for handling images in the saving and loading process.
*/ 
public class ImageFile {

	private String path;
	private BufferedImage image;
	
	/**
	* @param pathFile
	*/ 
	public ImageFile(String pathFile) {	
		try {
			File fileImage = new File(pathFile);
			image = ImageIO.read(fileImage);
			path = fileImage.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* @param image
	*/ 
	public ImageFile(BufferedImage image) {	
		this.image = image;
		path = null;
	}
	
	/**
	* @return the path to the image 
	*/ 
	public String getPath() { return path; }

	/**
	* @return the image object
	*/ 
	public Image getImage() { return image; }
	
	/**
	 * @return the image with its BufferedImage type
	 */
	public BufferedImage getBufferedImage() { return image; }
}
