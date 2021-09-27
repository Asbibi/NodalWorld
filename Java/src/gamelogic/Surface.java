package gamelogic;

import java.awt.Image;
import java.awt.Toolkit;
import java.lang.Object;

/**
* Each instance of this class represents a different kind of surface that can be stored on a tile.
* 
* @see TerrainLayer, Element
*/
public class Surface extends Element{

	private static int idCounter;

	private int id;

	/**
	* @param name
	*/ 
	public Surface(String name) {
		this(name, (Image)null);
	}
	/**
	* @param name
	* @param image path
	*/ 
	public Surface(String name, String imagePath) {
		this(name, Toolkit.getDefaultToolkit().getImage(imagePath));
	}
	/**
	* @param name
	* @param image
	*/ 
	public Surface(String name, Image image) {
		id = idCounter;
		idCounter++;
		this.name = name;
		this.image = image;
	}	

	/**
	* Checks the equality between two surfaces by verifying that their ids are the same.
	*/ 
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Surface)) return false;
		Surface surf = (Surface) o;
		return (this.id == surf.id);
	}


	
	
	
	
	
	
	
	
	// =========================		Statis fields		===================================
	
	
	
	private static Surface empty = new Surface("empty");

	/**
	* @return an "empty" surface used as a default values in terrains
	*/ 
	public static Surface getEmpty() {
		return empty;
	}
}
