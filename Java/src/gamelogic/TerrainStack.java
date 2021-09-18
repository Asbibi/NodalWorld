package gamelogic;

import java.util.LinkedList;

/**
* This class represents the model used to stack terrains on top of each other (like Photoshop layers).
* 
* @see Terrain
*/ 
public class TerrainStack {

	private LinkedList<Terrain> stack;

	/**
	*
	*/
	public TerrainStack() {
		stack = new LinkedList<Terrain>();
	}

	/**
	* @param terrain the terrain to be pushed on top of the stack
	*/
	public void pushTerrain(Terrain terrain) {
		stack.addFirst(terrain);
	}

	/**
	* @param pos
	* @return the surface found at position pos, and the empty surface if none is found
	* @see Surface.getEmpty
	*/
	public Surface surfaceAt(Vec2D pos) {
		if(stack.isEmpty()) return Surface.getEmpty();
		for(Terrain terrain : stack) {
			Surface surf = terrain.surfaceAt(pos);
			if(!surf.equals(Surface.getEmpty())) return surf;
		}
		return Surface.getEmpty();
	}

}
