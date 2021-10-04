package gamelogic;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

/**
* This class represents the model used to stack terrains on top of each other (like Photoshop layers).
* 
* @see TerrainLayer
*/ 
public class TerrainStack {
	private Dimension stackDimensions;
	private Integer triggerTime;

	private LinkedList<TerrainLayer> stack;

	/**
	* Call the other constructor with default trigger time set at 10
	*/
	public TerrainStack() {
		this(10);
	}
	/**
	* @param triggerPeriod will be the trigger time of the terrain
	*/
	public TerrainStack(int triggerPeriod) {
		stack = new LinkedList<TerrainLayer>();
		stackDimensions = new Dimension(0,0);
		triggerTime = triggerPeriod;
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
	* @return the terrainLayer list that composes the terrain stack
	*/
	public List<TerrainLayer> getStack() {
		return stack;
	}

	/**
	* The dimensions of the stack are defined as the max height and max width among all the terrain layers
	* @return the dimensions of the stack
	*/
	public Dimension getStackDimension() {
		return stackDimensions;
	}


	/**
	* @return the trigger time
	*/
	public int getTriggerTime() {
		return triggerTime;
	}

	/**
	* @param the new trigger time to use
	*/
	public void setTriggerTime(int time) {
		triggerTime = time;
	}
}
