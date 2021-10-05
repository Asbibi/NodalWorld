package gamelogic;

import java.util.List;
import java.util.ArrayList;

/**
* Representation of the terrain as a list of slots.
* An instance of this class also defines spatial boundaries (width and height).
* It gets updated regularly (trigger time). 
* 
* @see TerrainSlot
*/ 
public class Terrain {

	private int width, height;
	private int triggerTime;
	private List<TerrainSlot> slots;

	/**
	* @param width
	* @param height
	* @param triggerTime
	*/ 
	public Terrain(int width, int height, int triggerTime) {
		this.width = width;
		this.height = height;
		this.triggerTime = triggerTime;
		slots = new ArrayList<TerrainSlot>();
	}

	/**
	* @return the terrain's width
	*/ 
	public int getWidth() {
		return width;
	}

	/**
	* @return the terrain's height
	*/ 
	public int getHeight() {
		return height;
	}

	/**
	* @param triggerTime
	*/ 
	public void setTriggerTime(int triggerTime) {
		this.triggerTime = triggerTime;
	} 

	/**
	* @return the trigger time for updating the terrain
	*/ 
	public int getTriggerTime() {
		return triggerTime;
	}

	/**
	* @param pos
	* @return the surface element in the tile at the given position
	*/ 
	public Surface getSurfaceAt(Vec2D pos) {
		if(slots.isEmpty()) return Surface.getEmpty();
		for(TerrainSlot slot : slots) {
			if(slot.isOccupied()) {
				Surface surface = slot.getTerrainNode().getSurfaceAt(pos);
				if(!surface.equals(Surface.getEmpty())) return surface;
			}
		}
		return Surface.getEmpty();
	}

	/**
	* Add a new slot on top of the others
	*/ 
	public void addSlot() {
		slots.add(0, new TerrainSlot());
	}

	/**
	* @return the list of slots
	*/ 
	public List<TerrainSlot> getSlots() {
		return slots;
	}

	/**
	* @param index
	* @return the slot at the given index (if it exists)
	*/ 
	public TerrainSlot getSlot(int index) {
		return slots.get(index);
	}

}