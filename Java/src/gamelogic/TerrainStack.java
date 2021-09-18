package gamelogic;

import java.util.LinkedList;

public class TerrainStack {

	private LinkedList<Terrain> stack;

	public TerrainStack() {
		stack = new LinkedList<Terrain>();
	}

	public void pushTerrain(Terrain terrain) {
		stack.addFirst(terrain);
	}

	public Surface surfaceAt(Vec2D pos) {
		if(stack.isEmpty()) return Surface.getEmpty();
		for(Terrain terrain : stack) {
			Surface surf = terrain.surfaceAt(pos);
			if(!surf.equals(Surface.getEmpty())) return surf;
		}
		return Surface.getEmpty();
	}

}
