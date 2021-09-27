package gamelogic;

import java.awt.Dimension;
import java.util.LinkedList;

/**
* This class represents the model used to stack terrains on top of each other (like Photoshop layers).
* 
* @see TerrainLayer
*/ 
public class TerrainStack {
	private Dimension stackDimensions;

	private LinkedList<TerrainLayer> stack;

	/**
	*
	*/
	public TerrainStack() {
		stack = new LinkedList<TerrainLayer>();
		stackDimensions = new Dimension(0,0);
	}

	/**
	* @param terrain the terrain to be pushed on top of the stack
	*/
	public void pushTerrain(TerrainLayer terrain) {
		if (terrain != null)
		{
			stack.addFirst(terrain);
			stackDimensions.height = Math.max(stackDimensions.height, terrain.getHeight());
			stackDimensions.width = Math.max(stackDimensions.width, terrain.getWidth());			
		}
	}

	/**
	* @param pos
	* @return the surface found at position pos, and the empty surface if none is found
	* @see Surface.getEmpty
	*/
	public Surface getSurfaceAt(Vec2D pos) {
		if(stack.isEmpty()) return Surface.getEmpty();
		for(TerrainLayer terrain : stack) {
			Surface surf = terrain.surfaceAt(pos);
			if(!surf.equals(Surface.getEmpty())) return surf;
		}
		return Surface.getEmpty();
	}

	/**
	* The dimensions of the stack are defined as the max height and max width among all the terrain layers
	* @return the dimensions of the stack
	*/
	public Dimension getStackDimension() {
		return stackDimensions;
	}

}
