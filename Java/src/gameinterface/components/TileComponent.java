package gameinterface.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import gameinterface.WorldPanel;
import gamelogic.Surface;

/**
* This display a single tile of the world, with its surface and entities.
* This class is the model part of the tile in the MVC conception.
* TileView is the view part.
* There is no Controller part as the user can't interact on the tiles, they're just here for display purposes.
* 
* @see WorldPanel, Surface, TileView
*/ 
public class TileComponent extends JComponent{
	private TileView view;
	
	private Surface currentSurface;
	private ArrayList<Integer> currenSpeciesCounts;
	private WorldPanel owner;
	
	
	static int tileIdCount;
	public int tileId;
	
	public TileComponent(WorldPanel owner) {
		this.owner = owner;
		currentSurface = Surface.getEmpty();
		currenSpeciesCounts = new ArrayList<Integer>();
		view = new TileView();
		
		tileId = tileIdCount;
		tileIdCount++;
	}
	
	@Override
    public void paintComponent(Graphics pen) {
    	view.paint((Graphics2D)pen, this);
    }
	
	
	/**
	* @param surface the tile must display
	*/
	public void setSurface(Surface surface) {
		currentSurface = surface;
		revalidate();
	}
	/**
	* Replace the current species counts array by an new array of the correct size filled with 0. 
	* @param the new number of species (ie the size of the new array)
	*/
	public void setEmptyArraySpecies(int speciesNumber) {
		currenSpeciesCounts = new ArrayList<Integer>(speciesNumber);
		for (int i = 0; i < speciesNumber; i++)
			currenSpeciesCounts.add(0);
	}
	/**
	* Set the count of a species to 0.
	* @param index of the species to reset
	*/
	public void resetCountArraySpecies(int speciesIndex) {
		System.out.println(speciesIndex);
		currenSpeciesCounts.set(speciesIndex, 0);
	}
	/**
	* Increase the current count of a species of 1.
	* @param index of the species to increase
	*/	
	public void incrementCountArraySpecies(int speciesIndex) {
		currenSpeciesCounts.set(speciesIndex, currenSpeciesCounts.get(speciesIndex) + 1);
	}	
	
	

	/**
	* Mainly used by the view.
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
	* Should be equal to the total amount of species in this world 
	* @return the number of species that has to be displayed
	*/
	public int getSpeciesNumber() { return currenSpeciesCounts.size(); }
	/**
	* @param index of the species the count is asked
	* @return the current count of entities of the species on this tile
	*/
	public int getSpeciesCount(int speciesIndex) { return currenSpeciesCounts.get(speciesIndex); }
}
