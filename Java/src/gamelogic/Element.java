package gamelogic;

import java.awt.Image;
import java.io.Serializable;

/**
* A simple class that hold a name and image attributes, useful for reusing interface elements on species and surfaces
* 
* @see Entity
* @see Surface
* @see Species
*/ 
public class Element implements Serializable {

	private static final long serialVersionUID = 221225820502337258L;
	protected String name = "";
	protected transient ImageFile image = null;
	
	/**
	* @param name
	* @param imagePath
	*/ 
	public Element(String name, String imagePath) {
		setName(name);
		image = imagePath == null ? null : new ImageFile(imagePath);
	}
	
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
		if (image != null)
			return image.getImage();
		return null;
	}	

	/**
	* @return the image with its file path
	*/ 
	public ImageFile getImageFile() {
		return image;
	}

	/**
	* @param the new name for this element
	*/
	public void setName(String name) { this.name = name; }

	/**
	* @param the path to the new image for this element
	*/
	public void setImageFile(ImageFile image) { this.image = image; }
}
