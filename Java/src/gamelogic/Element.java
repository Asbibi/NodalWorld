package gamelogic;

import java.awt.Image;
import java.awt.Toolkit;

/**
* A simple class that hold a name and image attributes, useful for reusing interface elements on species and surfaces
* 
* @see Entity
* @see Surface, Species
*/ 
public class Element {
	protected String name = "";
	protected Image image = null;
	
	
	/**
	* @return the name of the element
	*/ 
	@Override
	public String toString() {
		return name;
	}	
	/**
	* @return the image for display of the element
	*/ 
	public Image getImage() {
		return image;
	}
	


	/**
	* @param the new name for this element
	*/
	public void setName(String name) { this.name = name; }
	/**
	* @param the new image for this element
	*/
	public void setImage(Image image) { this.image = image; }
	/**
	* @param the path to the new image for this element
	*/
	public void setImage(String imagePath) { this.image = Toolkit.getDefaultToolkit().getImage(imagePath); }
}
