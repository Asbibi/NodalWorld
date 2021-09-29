package gameinterface;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import gamelogic.Entity;
import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.TerrainStack;
import gamelogic.Vec2D;

/**
* This class manages how the world will be displayed.
* 
* @see TerrainStack, TileComponent
*/ 
public class WorldPanel extends JPanel{
	
	// ==== Tile used fields ====
	private Color emptyColor = new Color(89,71,75);
	private Color missingSurfaceImageColor = new Color(255,75,255);
	private Color missingSpeciesImageColor = new Color(235,125,235);
	private Color speciesCountColor = Color.white;
	private Color speciesCountBGColor = new Color(0,0,0,150);
	private Color gridColor = new Color(162,255,25);
	private int gridThickness = 1;
	private boolean displayGridDetail = true;

	// ==== Class fields ====
	private Dimension gridDimension;
	private ArrayList<TileComponent> tiles;
	private List<Species> speciesDisplayed;
	private TerrainStack terrain;
	private int speciesSlotNumber_sqrt;
	private int minimalTileSize;
	

	
	public WorldPanel(GameManager gameManager) {
		setBackground(Color.blue);
		tiles = new ArrayList<TileComponent>();
		speciesDisplayed = gameManager.getSpeciesArray();
		terrain = gameManager.getTerrainStack();
		gridDimension = new Dimension(-1,-1);
		speciesSlotNumber_sqrt = 0;
		ResetMinimalTileSize();
	}
	
	public void update(int frame) {
		updateTerrain();
		for (int i =0; i < speciesDisplayed.size(); i++) {
			if(speciesDisplayed.get(i).trigger(frame))
				updateSpeciesDisplay(i);
		}
		repaint();
	}
	
	
	
	// ==== Size Related ====
	public void ResetMinimalTileSize() {
		minimalTileSize = 32;
	}
	public void increaseMinimalTileSize() {
		minimalTileSize += 16;
	}
	public void decreaseMinimalTileSize() {
		minimalTileSize += 16;
	}
	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		setPreferredSize(d);
	}
	@Override
	public void setPreferredSize(Dimension d) {
		//System.out.print("Set preferred size on world panel" + d);
		int tileSizeX = d.width / gridDimension.width;
		int tileSizeY = d.height / gridDimension.height;
		int realTileSize = Math.min(tileSizeX, tileSizeY);
		realTileSize = Math.max(realTileSize, minimalTileSize);
		d.width = realTileSize * gridDimension.width;
		d.height = realTileSize * gridDimension.height;
		//System.out.println("\t Became :" + d);
		super.setPreferredSize(d);
	}
	/**
	 * Set automatically a preferred size to fit the tile size.
	 * Still some work to do on this part.
	*/
	public void setMinimalPreferredSize() {
		setPreferredSize(new Dimension(0,0));
	}
	
	
	
	// ==== Surface Related ====
	
	/**
	 * Updates the whole terrain, including dimension check and potential changed
	* @param the terrain stack to display
	*/
	public void updateTerrain() {
		if (terrain == null) {
			gridDimension = new Dimension(-1,-1);			
		} else {
			setDimensions(terrain.getStackDimension());
			updateTilesSurface(terrain);
		}
	}	
	

	/**
	 * If the dimensions are the same as the previous ones, it wont do nothing. Otherwise, it will adapt the layout and create enough tiles to fill the entire map.
	* @param the new Dimensions (should be got from the terrain to display)
	*/
	private void setDimensions(Dimension newDimensions) {
		if (gridDimension == newDimensions)
			return;
				
		int count = newDimensions.width * newDimensions.height;
		removeAll();	// removes all tiles from the JPanel
		
		// if one dimension is 0, the terrain is invalid and thus soulhn't be displayed
		if (count == 0) {
			tiles.clear();
			return;
		}
		
		while (tiles.size() > count)
			tiles.remove(0);		// remove from the list
		while (tiles.size() < count)
			tiles.add(new TileComponent(this));		// add a new tile to the list
		
		setLayout (new GridLayout(newDimensions.height, newDimensions.width));	// layout changed after removing extra tiles and after adding missing tiles to make sure there is always at most the number of tiles in the layout

		int width = newDimensions.width;
		for (int y = 0; y < newDimensions.height; y++)
			for (int x = 0; x < newDimensions.width; x++)
				add(tiles.get(x + y * width));
		
		gridDimension = newDimensions;
		updateAllSpeciesDisplay();
		setMinimalPreferredSize();	// changing size
	}
	
	/**
	* Only updates the surfaces displayed.
 	* This method assumes that the dimensions of the terrain are the same as gridDimension.
	* @param the terrain stack to represent
	*/
	private void updateTilesSurface(TerrainStack terrain) {
		int width = gridDimension.width;
		for (int x = 0; x < gridDimension.width; x++)
		{
			for (int y = 0; y < gridDimension.height; y++)
				tiles.get(x + y * width).setSurface(terrain.getSurfaceAt(new Vec2D(x,y)));
		}
	}

	

	
	
	
	// ==== Species Related ====
	
	
	/**
	 * This method should only be called when a species is added or removed. For usual game logic loop just use updateOneSpeciesDisplayed
	* @param the array of species that should be displayed
	* @see updateOneSpeciesDisplayed
	*/
	public void updateAllSpeciesDisplay() {
		boolean hasChanged = computeSpeciesSlotNumber_sqrt(speciesDisplayed.size());	// set speciesSlotNumber_sqrt
		if (hasChanged) {
			// reset all tiles arrays to fit the new potential size
			for (int i = 0; i < tiles.size() ; i++) 
				tiles.get(i).setEmptyArraySpecies(speciesSlotNumber_sqrt * speciesSlotNumber_sqrt);
			for (int s = 0; s < speciesDisplayed.size(); s++)
				updateSpeciesDisplay(s);			
		}
	}
	
	/**
	 * Actually calls updateSpeciesDisplay(int speciesIndex) after retrieving the index
	* @param the species to update
	*/
	public void updateSpeciesDisplay(Species speciesToUpdate) {
		updateSpeciesDisplay(speciesDisplayed.indexOf(speciesToUpdate));
	}
	
	/**
	 * This assumes that the correct number of slots has already been computed and setted on the tiles
	 * Also assumes that there wont be any entity outside of the terrain bounds defined by gridDimension
	 * (no checks)
	* @param the index of the species to update
	*/
	public void updateSpeciesDisplay(int speciesIndex) {
		// for each tiles, reset the count of the species to 0
		for (int i = 0; i < tiles.size() ; i++)
			tiles.get(i).resetCountArraySpecies(speciesIndex);
		
		// for each entity of the species, inc the species count
		int width = gridDimension.width;
		for(Entity entity : speciesDisplayed.get(speciesIndex).getMembers())
			tiles.get(entity.getPos().getX() + entity.getPos().getY() * width).incrementCountArraySpecies(speciesIndex);
	}
	
	/**
	 * Compute the number of slots needed and set the value found to the class attribute speciesSlotNumber_sqrt
	* @param the number of species to potentially display in a single tile
	* @return returns if the number of slots needed changed
	*/
	private boolean computeSpeciesSlotNumber_sqrt(int numberOfSpecies) {
		int _speciesSlotNumber_sqrt = (int)Math.sqrt(numberOfSpecies);
		if (_speciesSlotNumber_sqrt * _speciesSlotNumber_sqrt < numberOfSpecies)	//check if it's the perfect square (ex 4,9) or if the next square is needed (ex 5->9, 18->25)
			_speciesSlotNumber_sqrt++;
		
		if (_speciesSlotNumber_sqrt != speciesSlotNumber_sqrt) {
			speciesSlotNumber_sqrt = _speciesSlotNumber_sqrt;
			return true;
		} else
			return false;
	}
	
	
	
	
	// ==== Setters ====
	
	public void flipDisplayGridDetail() {
		displayGridDetail = !displayGridDetail;
		repaint();
	}
	
	

	// ==== Getters ====
	
	/**
	* @return square root of the number of slot needed to display all species on one tile
	*/
	public int getSpeciesSlotNumber_sqrt() { return speciesSlotNumber_sqrt;}
	/**
	* @param index of the species the image is required
	* @return image of the species indicated
	*/
	public Image getSpeciesImage(int speciesIndex) { return speciesDisplayed.get(speciesIndex).getImage(); }
	/**
	* @return color to use when the surface is empty
	*/
	public Color getEmptyColor() { return emptyColor; }
	/**
	* @return color to use when the surface isn't empty but the image is missing
	*/
	public Color getMissingSurfaceImageColor() { return missingSurfaceImageColor; }
	/**
	* @return color to use when the image of the species is missing or null
	*/
	public Color getMissingSpeciesImageColor() { return missingSpeciesImageColor; }
	/**
	* @return color of the count indication text
	*/
	public Color getSpeciesCountColor() { return speciesCountColor; }
	/**
	* @return color of the count indication background
	*/
	public Color getSpeciesCountBGColor() { return speciesCountBGColor; }
	/**
	* @return color of the grid (if shown)
	*/
	public Color getGridColor() { return gridColor; }
	/**
	* @return half thickness of the grid (if shown)
	*/
	public int getGridThickness() { return gridThickness; }
	/**
	* @return indicates if the grid should be displayed
	*/
	public boolean getDisplayGridDetail() { return displayGridDetail; }
}
