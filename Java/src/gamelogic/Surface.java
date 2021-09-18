package gamelogic;

import java.lang.Object;

/**
* @see Terrain
*/
public class Surface {

	private static int idCounter;

	private int id;
	private String name;

	public Surface(String name) {
		id = idCounter;
		idCounter++;
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Surface)) return false;
		Surface surf = (Surface) o;
		return (this.id == surf.id);
	}

	public String getName() {
		return name;
	}

	private static Surface empty = new Surface("empty");

	public static Surface getEmpty() {
		return empty;
	}

}
