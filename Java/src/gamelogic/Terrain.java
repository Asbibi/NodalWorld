package gamelogic;

/**
* A terrain is a 2D grid of tiles, each tile containing a surface.
* The dimensions of a terrain are given at construction time and do not change.
* 
* @see Surface
*/
public class Terrain {

	private Integer width, height;
	private Surface[] tiles;

	public Terrain(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		tiles = new Surface[width*height];
		fill(Surface.getEmpty());
	}

	private void fill(Surface surface) {
		for(int i=0; i<width*height; i++) {
			tiles[i] = surface;
		}
	}

	public void setSurfaceAt(Vec2D pos, Surface surf) {
		if(!isOutside(pos)) {
			tiles[pos.getX()+width*pos.getY()] = surf;
		}
	}

	public Surface surfaceAt(Vec2D pos) {
		if(isOutside(pos)) return Surface.getEmpty();
		return tiles[pos.getX()+width*pos.getY()];
	}

	private boolean isOutside(Vec2D pos) {
		return (pos.getX() < 0) || (pos.getX() >= width) || (pos.getY() < 0) || (pos.getY() >= height);
	}

}
