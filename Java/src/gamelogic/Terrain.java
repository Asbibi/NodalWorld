package gamelogic;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
* Representation of the terrain as a list of slots.
* An instance of this class also defines spatial boundaries (width and height).
* It gets updated regularly (trigger time). 
* 
* @see TerrainSlot
*/ 
public class Terrain implements Serializable {


	// ========== MEMBERS VARIABLES ==========

	private int width, height;
	private int triggerTime;
	private List<TerrainSlot> slots;

	private transient List<ChangeListener> listeners;


	// ========== INITIALIZATION ==========

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
		listeners = new LinkedList<ChangeListener>();
	}
	
	/**
	* Utility method used when loading a terrain from a saved project.
	*/ 
	public void initTransientFields() {
		if (listeners == null)
			listeners = new LinkedList<ChangeListener>();
	}

	// ========== BASIC GETTERS AND SETTERS ==========

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
	* @param frame
	* @return true if terrain is triggered at the given frame, otherwise false
	*/ 
	public boolean trigger(Integer frame) {
		return (frame%triggerTime == 0);
	}


	// ========== METHOD USED FOR FINAL TERRAIN RENDERING ==========

	/**
	* @param pos
	* @return the surface element in the tile at the given position, the empty surface if nothing is found or if the position is outside the terrain
	*/ 
	public Surface getSurfaceAt(Vec2D pos) {
		if(slots.isEmpty()) return Surface.getEmpty();
		//for(int i=slots.size()-1; i>= 0; i--) {
		for(int i = 0; i < slots.size(); i++) {
			TerrainSlot slot = slots.get(i);
			if(slot.isOccupied()) {
				Surface surface = slot.getTerrainNode().getSurfaceAt(pos);
				if(!surface.equals(Surface.getEmpty())) return surface;
			}
		}
		return Surface.getEmpty();
	}


	// ========== SLOT MANIPULATION ==========

	/**
	* Add a new slot on top of the others (end of list)
	*/ 
	public void addSlot() {
		addSlot(-1);
	}

	/**
	* Add a new slot at the specified index
	* 
	* @param index the index of the new slot (0 = bottom of the list)
	*/ 
	public void addSlot(int index) {
		if (index < 0 || index > slots.size())
			index = slots.size();
		slots.add(index, new TerrainSlot());
		triggerChangeListeners();
	}

	/**
	* Swap the slots at the given indexes
	* 
	* @param indexFirst
	* @param indexSecond
	*/ 
	public void swapSlots(int indexFirst, int indexSecond) {
		if (indexFirst < 0 || indexFirst >= slots.size() || indexSecond < 0 || indexSecond >= slots.size())
			return;
		TerrainSlot tempSlot = slots.get(indexFirst);
		slots.set(indexFirst, slots.get(indexSecond));
		slots.set(indexSecond, tempSlot);
		triggerChangeListeners();
	}

	/**
	* Remove the top slot
	*/ 
	public void removeSlot() {
		removeSlot(-1);
	}

	/**
	* Remove the slot at the given index
	* 
	* @param index
	*/ 
	public void removeSlot(int index) {
		if (index < 0 || index >= slots.size())
			index = slots.size() - 1;
		slots.remove(index);
		triggerChangeListeners();
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


	// ========== CHANGE LISTENERS ==========

	/**
	* @param listener
	*/ 
	public void addChangeListener(ChangeListener listener) { listeners.add(listener); }

	/**
	* Notify listeners when something related to the terrain happens
	*/ 
	private void triggerChangeListeners() {
		for(ChangeListener listener : listeners) 
			listener.stateChanged(new ChangeEvent(this));
	}

}
