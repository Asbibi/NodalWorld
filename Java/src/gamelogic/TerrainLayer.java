package gamelogic;

/**
* A terrain is a 2D grid of tiles, each tile containing a surface.
* The dimensions of a terrain are given at construction time and do NOT change (important for the terrain stack dimensions).
* 
* @see Surface
*/
public class TerrainLayer {

	private Integer width, height;
	private Surface[] tiles;

	/**
	* @param width
	* @param height
	*/
	public TerrainLayer(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		tiles = new Surface[width*height];
		fill(Surface.getEmpty());
	}

	/**
	* Fills the Terrain with the given surface.
	* 
	* @param surface
	*/
	public void fill(Surface surface) {
		for(int i=0; i<width*height; i++) {
			tiles[i] = surface;
		}
	}

	/**
	* Sets the tile at a given position to store a given surface.
	* 
	* @param pos
	* @param surf
	*/
	public void setSurfaceAt(Vec2D pos, Surface surf) {
		if(!isOutside(pos)) {
			tiles[pos.getX()+width*pos.getY()] = surf;
		}
	}

	/**
	* @param pos
	* @return the surface found at position pos, and the empty surface if none is found
	* @see Surface.getEmpty
	*/
	public Surface surfaceAt(Vec2D pos) {
		if(isOutside(pos)) return Surface.getEmpty();
		return tiles[pos.getX()+width*pos.getY()];
	}

	private boolean isOutside(Vec2D pos) {
		return (pos.getX() < 0) || (pos.getX() >= width) || (pos.getY() < 0) || (pos.getY() >= height);
	}
	
	/**
	* @return the height of the terrain layer
	*/
	public Integer getHeight() { return height; }
	/**
	* @return the width of the terrain layer
	*/
	public Integer getWidth() { return width; }

}
