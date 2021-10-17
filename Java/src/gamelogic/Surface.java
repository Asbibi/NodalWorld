package gamelogic;

import java.awt.Color;
import java.lang.Object;
import java.util.List;

/**
* Each instance of this class represents a different kind of surface that can be stored on a tile.
* 
* @see TerrainLayer
* @see Element
*/
public class Surface extends Element{

	private static int idCounter;

	private int id;
	private Color color;

	/**
	* @param name
	*/ 
	public Surface(String name) {
		this(name, null);
	}

	/**
	* @param name
	* @param image path
	*/ 
	public Surface(String name, String imagePath) {
		super(name, imagePath);
		id = idCounter;
		idCounter++;
		color = Color.lightGray;
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
	
	/**
	* @return the surface color
	*/ 
	public Color getColor() {
		return color;
	}

	/**
	* @param color
	*/ 
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	// ========== EMPTY SURFACE ==========
	
	private static Surface empty = new Surface("empty");

	/**
	* @return an "empty" surface used as a default values in terrains
	*/ 
	public static Surface getEmpty() {
		return empty;
	}
	
	/**
	* This method synchronizes the idCounter on a bunch of surfaces, so that the id of a new surface will be coherent with the ids of the surfaces gave.
	* This method can only make the idCounter increase, which will happen if the idCounter is lower or equal at the id of one of the surfaces gave. 
	* @param surfaces the list of the surfaces to sync the idCounter on
	*/ 
	public static void synchIdCounter(List<Surface> surfaces) {
		int lastId = 0;
		for (Surface surf : surfaces)
			lastId = Math.max(lastId, surf.id);
		idCounter = Math.max(idCounter, lastId+1);	// +1 because we want the counter to be the id of the NEXT surface, and not the newer one
	}
}
