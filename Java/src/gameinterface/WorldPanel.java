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
* This class manages how the display of the world.<br/>
* The display consists of grid of tile representing the map. The grid has the terrain dimension and each tile represent the surface at its position (surface being the first non empty Surface returned by a TerrainSlot, begining with the one on top).<br/>
* The tiles can display their surface using the surface's image as texture or the surface's color for a simplified display.<br/><br/>
* 
* Then the species are displayed. On each tile, if the species is present its image will be displayed. Plus, if there is more then 1 member, the count will be displayed on the top left corner of the species as well.<br/>
* Each species will always take the same part of the available area in the tile, area that is divided depending on the number of species.<br/><br/>
* 
* WorldPanel has also possibility to display a green grid over the map in order to better demarcate the tile.<br/>
* And it has zoom in/out/reset features.
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
	private int perfectTileSize;
	

	/**
	* @param gameManager the gameManager that is represented
	*/
	public WorldPanel(GameManager gameManager) {
		tiles = new ArrayList<TileComponent>();
		gridDimension = new Dimension(-1,-1);
		speciesDisplayed = new ArrayList<>();
		terrain = null;
	}
	
	/**
	* @param gameManager the gameManager that is represented
	*/
	public  void connectGameManager(GameManager gameManager) {
		speciesDisplayed = gameManager.getSpeciesArray();
		terrain = gameManager.getTerrain();
		speciesSlotNumber_sqrt = 0;
		
		computeMinimalTileSize();
		resetTileSizeToMinimal();
	}

	/**
	* Updates the terrain and the species that need to be.
	* Having the frame allows to only update the species that were triggered during the computed frame.
	* @param frame the frame that had just been computed by the gamemanager
	*/
	public void updateMap(int frame) {
		updateTerrain();
		for (int i = 1; i < speciesDisplayed.size(); i++) {	// starting at 1 to prevent using the empty species
			if(speciesDisplayed.get(i).trigger(frame))
				updateSpeciesDisplay(i);
		}
		repaint();
	}
	
	
	
	// ==== Size Related ====

	/**
	* Resets the zoom so the map fits as much as possible in the available space.<br/>
	* Taht corresponds to setting the tile size to perfectTileSize. 
	*/
	public void resetTileSizeToMinimal() {
		usedTileSize = perfectTileSize;
		revalidate();
	}
	/**
	* Zooms in until tile size is 600 pixels.
	*/
	public void increaseTileSize() {
		usedTileSize *= 1.5;
		if(usedTileSize > 600)
			usedTileSize = 600;
		revalidate();
	}
	/**
	* Zooms out until tile size is perfectTileSize / 4.
	*/
	public void decreaseTileSize() {
		usedTileSize *= 0.75;
		if (usedTileSize < perfectTileSize/4)
			usedTileSize = perfectTileSize/4;
		revalidate();
	}
	/**
	* Compute perfectTileSize, the perfect tile size to use.<br/>
	* The result depends on the worldPanel's parent's size (i.e. the available display area dimension).
	*/
	public void computeMinimalTileSize() {
		if (gridDimension == null || gridDimension.width == 0 || gridDimension.height == 0) {
			perfectTileSize = 32;
			return;
		}
		
		Container parent = getParent();
		if (parent == null)
			return;
		Dimension d = parent.getParent().getSize();	// access the JScrollPane to get the available space dimension
		int tileSizeX = d.width / gridDimension.width;
		int tileSizeY = d.height / gridDimension.height;
		perfectTileSize = Math.min(tileSizeX, tileSizeY);
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
	* Updates the whole terrain, including dimension check and potential changed species.
	*/
	public void updateTerrain() {
		if (terrain == null) {
			gridDimension = new Dimension(-1,-1);			
		} else {
			updateDimensions();
			updateTilesSurface();
		}
	}	
	

	/**
	* If the dimensions are the same as the previous ones, it wont do anything.<br/>
	* Otherwise, it will adapt the layout and create enough tiles to fill the map, or remove to ones in excess.
	*/
	private void updateDimensions() {
		Dimension newDimensions = new Dimension(terrain.getWidth(), terrain.getHeight());
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
		updateAllSpeciesDisplay(true);
		computeMinimalTileSize();
		
        setPreferredSize(getPreferredSize());
	}
	
	/**
	* Only updates the surfaces displayed.
 	* This method assumes that the dimensions of the terrain are the same as gridDimension (which should be check by calling updateDimensions() before).
	*/
	private void updateTilesSurface() {
		int width = gridDimension.width;
		for (int x = 0; x < gridDimension.width; x++)
		{
			for (int y = 0; y < gridDimension.height; y++)
				tiles.get(x + y * width).setSurface(terrain.getSurfaceAt(new Vec2D(x,y)));
		}
	}

	

	
	
	
	// ==== Species Related ====
	
	
	/**
	* This method should only be called when a species is added or removed. For usual game logic loop just use updateOneSpeciesDisplayed(...).
	* @param forceChange indicates if should reset tiles' entity count array in any case or only if the count of species changed
	*/
	public void updateAllSpeciesDisplay(boolean forceChange) {
		boolean hasChanged = computeSpeciesSlotNumber_sqrt(speciesDisplayed.size() - 1);	// set speciesSlotNumber_sqrt ; -1 is because the first "species" in the list is the empty species
		if (hasChanged || forceChange) {
			// reset all tiles arrays to fit the new potential size
			for (int i = 0; i < tiles.size() ; i++) 
				tiles.get(i).setEmptyArraySpecies(speciesSlotNumber_sqrt * speciesSlotNumber_sqrt);
			for (int s = 1; s < speciesDisplayed.size(); s++)
				updateSpeciesDisplay(s - 1);			
		}
	}
	
	/**
	* Actually calls updateSpeciesDisplay(int speciesIndex) after retrieving the species's index index. Returns if it's the empty species.
	* @param the species to update
	*/
	public void updateSpeciesDisplay(Species speciesToUpdate) {
		if (!speciesToUpdate.equals(Species.getEmpty()))
			updateSpeciesDisplay(speciesDisplayed.indexOf(speciesToUpdate));
	}
	
	/**
	* This assumes that the correct number of slots has already been computed and setted on the tiles
	* Also assumes that there wont be any entity outside of the terrain bounds defined by gridDimension
	* To skip the empty surface, an index < 1 or greater than the last species index will make the method simply return. 
	* @param speciesIndex the index of the species to update
	*/
	public void updateSpeciesDisplay(int speciesIndex) {
		if (speciesIndex < 1 || speciesIndex >= speciesDisplayed.size())	// The first species of the list is always the empty species, so skip it
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
	* Computes the number of slots needed to display every species on one tile, and sets the value found to speciesSlotNumber_sqrt.
	* @param numberOfSpecies the number of species to potentially display in a single tile
	* @return returns if the number of slots needed changed or not
	*/
	private boolean computeSpeciesSlotNumber_sqrt(int numberOfSpecies) {
		int _speciesSlotNumber_sqrt = (int)Math.sqrt(numberOfSpecies);
		if (_speciesSlotNumber_sqrt * _speciesSlotNumber_sqrt < numberOfSpecies)	//check if it's a perfect square number (ex 4,9) or if the next square number is needed (ex 5->9, 18->25)
			_speciesSlotNumber_sqrt++;
		
		if (_speciesSlotNumber_sqrt != speciesSlotNumber_sqrt) {
			speciesSlotNumber_sqrt = _speciesSlotNumber_sqrt;
			return true;
		} else
			return false;
	}
	
	
	
	
	// ==== Setters ====

	/**
	* Flips the boolean indicating if the grid should be displayed on top of the tiles.
	*/
	public void flipDisplayGridDetail() {
		displayGridDetail = !displayGridDetail;
		repaint();
	}
	/**
	* Flips the boolean indicating if the tiles should display their surface with its images as texture or as square of its color.
	*/
	public void flipUseColorOverImage() {
		useColorOverImage = !useColorOverImage;
		repaint();
	}
	
	

	// ==== Getters ====
	
	/**
	* @return the square root of the number of slot needed to display all species on one tile
	*/
	public int getSpeciesSlotNumber_sqrt() { return speciesSlotNumber_sqrt;}
	/**
	* @param speciesIndex the index of the species of which the image is required
	* @return the image of the species indicated
	*/
	public Image getSpeciesImage(int speciesIndex) { return speciesDisplayed.get(speciesIndex).getImage(); }
	/**
	* @return the color to use when the surface is empty
	*/
	public Color getEmptyColor() { return emptyColor; }
	/**
	* @return the color to use when the surface isn't empty but the image is missing
	*/
	public Color getMissingSurfaceImageColor() { return missingSurfaceImageColor; }
	/**
	* @return the color to use when the image of the species is missing or null
	*/
	public Color getMissingSpeciesImageColor() { return missingSpeciesImageColor; }
	/**
	* @return the color of the member count text
	*/
	public Color getSpeciesCountColor() { return speciesCountColor; }
	/**
	* @return the color of the member count background
	*/
	public Color getSpeciesCountBGColor() { return speciesCountBGColor; }
	/**
	* @return the color of the grid
	*/
	public Color getGridColor() { return gridColor; }
	/**
	* @return the thickness of the grid
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
