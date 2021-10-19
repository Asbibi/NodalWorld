package gamelogic;

import java.io.Serializable;

/**
* Representation of a slot used to link the terrain network to the actual terrain model.
*  
* @see Terrain
* @see TerrainNode
*/ 
public class TerrainSlot implements Serializable {

	private static final long serialVersionUID = 7607023427483287328L;
	private boolean occupied;
	private TerrainNode terrainNode;

	/**
	*
	*/ 
	public TerrainSlot() {
		occupied = false;
		terrainNode = null;
	}

	/**
	* Connect the slot to the given terrain node 
	* 
	* @param terrainNode
	*/
	public void connect(TerrainNode terrainNode) {
		occupied = true;
		this.terrainNode = terrainNode;
	}

	/**
	* Disconnect the slot from any potential terrain node
	*/
	public void disconnect() {
		occupied = false;
		terrainNode = null;
	}

	/**
	* @return true if the slot is currently connected to a terrain node, otherwise false
	*/
	public boolean isOccupied() {
		return occupied;
	}

	/**
	* @return the current terrain node the slot is connected to, null if it is not connected
	*/ 
	public TerrainNode getTerrainNode() {
		return terrainNode;
	}

}
