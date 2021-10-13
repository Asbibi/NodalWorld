package gameinterface;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import gameinterface.components.TileComponent;
import gamelogic.Entity;
import gamelogic.GameManager;
import gamelogic.Species;
import gamelogic.Terrain;
import gamelogic.Vec2D;

/**
* This class manages how the world will be displayed.
* 
* @see TerrainStack
* @see TileComponent
* @see Terrain
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
	private boolean displayGridDetail = false;
	private boolean useColorOverImage = false;

	// ==== Class fields ====
	private Dimension gridDimension;
	private ArrayList<TileComponent> tiles;
	private List<Species> speciesDisplayed;
	private Terrain terrain;
	private int speciesSlotNumber_sqrt;
	private int usedTileSize;
	private int minimalTileSize;
	

	
	public WorldPanel(GameManager gameManager) {
		tiles = new ArrayList<TileComponent>();
		gridDimension = new Dimension(-1,-1);
		speciesDisplayed = new ArrayList<>();
		terrain = null;
	}
	
	public  void connectGameManager(GameManager gameManager) {
		speciesDisplayed = gameManager.getSpeciesArray();
		terrain = gameManager.getTerrain();
		speciesSlotNumber_sqrt = 0;
		
		computeMinimalTileSize();
		resetTileSizeToMinimal();
	}

	/**
	* Update the terrain and the species that need to.
	* Having the frame allows to only update the species that were triggered this frame
	* @param the frame that had just be computed by the gamemanager.
	*/
	public void updateMap(int frame) {
		updateTerrain();
		for (int i = 1; i < speciesDisplayed.size(); i++) {	// for starting at 1 to prevent using the empty species
			if(speciesDisplayed.get(i).trigger(frame))
				updateSpeciesDisplay(i);
		}
		repaint();
	}
	
	
	
	// ==== Size Related ====

	/**
	* Resets the zoom so the map fit completely in the available space;
	*/
	public void resetTileSizeToMinimal() {
		usedTileSize = minimalTileSize;
		revalidate();
	}
	/**
	* Zooms in until one tile is 600x600 pixels
	*/
	public void increaseTileSize() {
		usedTileSize *= 1.5;
		if(usedTileSize > 600)
			usedTileSize = 600;
		revalidate();
	}
	/**
	* Zooms out until one tile as the size of the minimal tile
	*/
	public void decreaseTileSize() {
		usedTileSize *= 0.75;
		if (usedTileSize < minimalTileSize)
			usedTileSize = minimalTileSize;
		revalidate();
	}
	/**
	* Compute the minimal tile size to use, depending on the parent's size which is the available size for the world
	*/
	public void computeMinimalTileSize() {
		if (gridDimension == null || gridDimension.width == 0 || gridDimension.height == 0) {
			minimalTileSize = 32;
			return;
		}
		
		Container parent = getParent();
		if (parent == null)
			return;
		Dimension d = parent.getParent().getSize();	// access the JScrollPane to get the available space dimension
		int tileSizeX = d.width / gridDimension.width;
		int tileSizeY = d.height / gridDimension.height;
		minimalTileSize = Math.min(tileSizeX, tileSizeY);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(usedTileSize * gridDimension.width, usedTileSize * gridDimension.height);
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
			setDimensions(new Dimension(terrain.getWidth(), terrain.getHeight()));
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
		computeMinimalTileSize();
		//System.out.println(newDimensions);
        setPreferredSize(getPreferredSize());
	}
	
	/**
	* Only updates the surfaces displayed.
 	* This method assumes that the dimensions of the terrain are the same as gridDimension.
	* @param the terrain stack to represent
	*/
	private void updateTilesSurface(Terrain terrain) {
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
		boolean hasChanged = computeSpeciesSlotNumber_sqrt(speciesDisplayed.size() - 1);	// set speciesSlotNumber_sqrt ; -1 is because the first "species" in the list is the empty species
		if (hasChanged) {
			// reset all tiles arrays to fit the new potential size
			for (int i = 0; i < tiles.size() ; i++) 
				tiles.get(i).setEmptyArraySpecies(speciesSlotNumber_sqrt * speciesSlotNumber_sqrt);
			for (int s = 1; s < speciesDisplayed.size(); s++)
				updateSpeciesDisplay(s - 1);			
		}
	}
	
	/**
	 * Actually calls updateSpeciesDisplay(int speciesIndex) after retrieving the index
	* @param the species to update
	*/
	public void updateSpeciesDisplay(Species speciesToUpdate) {
		if (!speciesToUpdate.equals(Species.getEmpty()))
			updateSpeciesDisplay(speciesDisplayed.indexOf(speciesToUpdate));
	}
	
	/**
	 * This assumes that the correct number of slots has already been computed and setted on the tiles
	 * Also assumes that there wont be any entity outside of the terrain bounds defined by gridDimension
	 * (no checks)
	* @param the index of the species to update
	*/
	public void updateSpeciesDisplay(int speciesIndex) {
		if (speciesIndex == 0)	// The first species of the list is always the empty species
			return;
		
		// for each tiles, reset the count of the species to 0
		for (int i = 0; i < tiles.size() ; i++)
			tiles.get(i).resetCountArraySpecies(speciesIndex -1);
		
		// for each entity of the species, increment the species count
		int width = gridDimension.width;
		for(Entity entity : speciesDisplayed.get(speciesIndex).getMembers())
			if (entity.isInArea(new Vec2D(0,0), new Vec2D(width, gridDimension.height)))
				tiles.get(entity.getPos().getX() + entity.getPos().getY() * width).incrementCountArraySpecies(speciesIndex -1);
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

	/**
	* Flips the boolean indicating if the grid should be displayed on top of the tiles
	*/
	public void flipDisplayGridDetail() {
		displayGridDetail = !displayGridDetail;
		repaint();
	}
	/**
	* Flips the boolean indicating if the tiles should be displayed with their images as texture or as square of their color
	*/
	public void flipUseColorOverImage() {
		useColorOverImage = !useColorOverImage;
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
	* @return indicates if the grid must be displayed
	*/
	public boolean getDisplayGridDetail() { return displayGridDetail; }
	/**
	* @return indicates if the tiles should be represented by their color or images
	*/
	public boolean getUseColorOverImage() { return useColorOverImage; }
}
