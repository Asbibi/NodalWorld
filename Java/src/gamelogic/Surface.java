package gamelogic;

import java.lang.Object;

/**
* Each instance of this class represents a different kind of surface that can be stored on a tile.
* 
* @see Terrain
*/
public class Surface {

	private static int idCounter;

	private int id;
	private String name;

	/**
	* @param name
	*/ 
	public Surface(String name) {
		id = idCounter;
		idCounter++;
		this.name = name;
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
	* @return the name of the surface
	*/
	@Override 
	public String toString() {
		return name;
	}

	private static Surface empty = new Surface("empty");

	/**
	* @return an "empty" surface used as a default values in terrains
	*/ 
	public static Surface getEmpty() {
		return empty;
	}

}
