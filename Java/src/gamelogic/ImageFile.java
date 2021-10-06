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
		//image = Toolkit.getDefaultToolkit().getImage(pathFile);
		//path = pathFile;		
		try {
			File fileImage = new File(pathFile);
			image = ImageIO.read(fileImage);
			path = fileImage.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPath() { return path; }
	public Image getImage() { return image; }
	
	/**
	 * Converts a given Image into a BufferedImage
	 * 
	 * === Method found on stack overflow : ===
	 * https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage#:~:text=One%20way%20to%20handle%20this%20is%20to%20create,then%20use%20getBufferedImage%20%28%29%20to%20get%20the%20BufferedImage.
	 *
	 * @return The converted BufferedImage
	 */
	public BufferedImage getBufferedImage()
	{
		/*if (image == null || image.getWidth(null) <=0 || image.getHeight(null) <= 0)
			return null;
		
	    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);	    
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(image, 0, 0, null);
	    bGr.dispose();
	    
	    return bimage;*/
		return image;
	}
}
