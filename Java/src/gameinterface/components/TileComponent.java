package gameinterface.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import gameinterface.WorldPanel;
import gamelogic.Surface;

/**
* This displays a single tile of the world, with its surface and entities.<br/>
* The surface part of the tile can be displayed using the Surface's Image like a texture or the Sufrcae's color to fill a plain square instead.
* Each tile keep a count of the entities present on it (no references, just an int per species in an array).
* 
* @see WorldPanel
* @see Surface
* @see TileView
*/ 
public class TileComponent extends JComponent{
	private WorldPanel owner;
	private TileView view;
	
	private Surface currentSurface;
	private ArrayList<Integer> currenSpeciesCounts;
	
	
	/**
	* @param owner the WorlPanel parent of the tile, used to retrieve surface or species informations during display
	*/
	public TileComponent(WorldPanel owner) {
		this.owner = owner;
		currentSurface = Surface.getEmpty();
		currenSpeciesCounts = new ArrayList<Integer>();
		view = new TileView();
	}
	
	@Override
    public void paintComponent(Graphics pen) {
    	view.paint((Graphics2D)pen, this);
    }
	
	
	/**
	* @param surface the surface that must be displayed
	*/
	public void setSurface(Surface surface) {
		currentSurface = surface;
		revalidate();
	}
	/**
	* Replaces the currenSpeciesCounts array by a new array of the correct size (which is the number of Species excluding the empty Species), filled with 0. 
	* @param speciesNumber the new number of species (i.e. the size of the new array)
	*/
	public void setEmptyArraySpecies(int speciesNumber) {
		currenSpeciesCounts = new ArrayList<Integer>(speciesNumber);
		for (int i = 0; i < speciesNumber; i++)
			currenSpeciesCounts.add(0);
	}
	/**
	* Sets the count of a species to 0.
	* @param speciesIndex the index of the species to reset
	*/
	public void resetCountArraySpecies(int speciesIndex) {
		currenSpeciesCounts.set(speciesIndex, 0);
	}
	/**
	* Increases the current count of a species of 1.
	* @param speciesIndex the index of the species to increase
	*/	
	public void incrementCountArraySpecies(int speciesIndex) {
		currenSpeciesCounts.set(speciesIndex, currenSpeciesCounts.get(speciesIndex) + 1);
	}	
	
	

	/**
	* @return the WorldPanel owning the tile
	*/	
	public WorldPanel getOwner() { return owner; }
	/**
	* @return the surface displayed
	*/
	public Surface getSurface() { return currentSurface; }
	/**
	* @return indicates if the tile has no surface or an empty surface
	*/
	public boolean isSurfaceEmpty() { return currentSurface == null || currentSurface == Surface.getEmpty(); }
	/**
	* If up to date, should be equal to the total amount of species in this world (excluding the empty Species)
	* @return the number of species that has to be displayed
	*/
	public int getSpeciesNumber() { return currenSpeciesCounts.size(); }
	/**
	* @param speciesIndex the index of the species of which the count is requested
	* @return the current count of entities of the species indicated (on this tile)
	*/
	public int getSpeciesCount(int speciesIndex) { return currenSpeciesCounts.get(speciesIndex); }
}
