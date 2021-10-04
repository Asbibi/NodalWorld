package gamelogic;

import java.util.List;
import java.util.ArrayList;

public class Terrain {

	private int width, height;
	private int triggerTime;
	private List<TerrainSlot> slots;

	public Terrain(int width, int height, int triggerTime) {
		this.width = width;
		this.height = height;
		this.triggerTime = triggerTime;
		slots = new ArrayList<TerrainSlot>();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setTriggerTime(int triggerTime) {
		this.triggerTime = triggerTime;
	} 

	public int getTriggerTime() {
		return triggerTime;
	}

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

	public void addSlot() {
		slots.add(0, new TerrainSlot());
	}

	public List<TerrainSlot> getSlots() {
		return slots;
	}

	public TerrainSlot getSlot(int index) {
		return slots.get(index);
	}

}
