package gamelogic;

public class TerrainSlot {

	private boolean occupied;
	private TerrainNode terrainNode;

	public TerrainSlot() {
		occupied = false;
		terrainNode = null;
	}

	public void connect(TerrainNode terrainNode) {
		occupied = true;
		this.terrainNode = terrainNode;
	}

	public void disconnect() {
		occupied = false;
		terrainNode = null;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public TerrainNode getTerrainNode() {
		return terrainNode;
	}

}
