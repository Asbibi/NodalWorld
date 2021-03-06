package gameinterface.components;

import java.awt.*;

import gameinterface.WorldPanel;

/**
* This class is responsible for the view part of the TileComponent.
* 
* @see TileComponent
*/ 
public class TileView {

	/**
	* @param graphics2D the Graphic Context to use
	* @param component the tile component to display
	*/ 
	public void paint(Graphics2D g, TileComponent tile) {
		paintSurface(g, tile);
		paintSpecies(g, tile);
		
		if (tile.getOwner().getDisplayGridDetail())
			paintGrid(g, tile);
	}
	
	
	/**
	* Paints the surface of the tile.
	* @param graphics2D the Graphic Context to use
	* @param component the tile component to display
	*/
	private void paintSurface(Graphics2D g, TileComponent tile) {
		if (tile.isSurfaceEmpty()) {
			g.setColor(tile.getOwner().getEmptyColor());
	        g.fillRoundRect(0, 0, tile.getWidth(), tile.getHeight(), 0, 0);
		} else if (tile.getOwner().getUseColorOverImage()) {
			g.setColor(tile.getSurface().getColor());
	        g.fillRoundRect(0, 0, tile.getWidth(), tile.getHeight(), 0, 0);
		} else {
			Image tileImage = tile.getSurface().getImage();
			if (tileImage == null || tileImage.getWidth(null) < 1) {
				g.setColor(tile.getOwner().getMissingSurfaceImageColor());
		        g.fillRoundRect(0, 0, tile.getWidth(), tile.getHeight(), 0, 0);
			} else {
		        g.drawImage(tileImage, 0, 0, tile.getWidth(), tile.getHeight(), tile);
			}
		}		
	}
	
	/**
	* Paints the species on the tile with their count if greater than one.
	* @param graphics2D the Graphic Context to use
	* @param component the tile component to display
	*/
	private void paintSpecies(Graphics2D g, TileComponent tile) {
		int maxSpeciesPerLine = tile.getOwner().getSpeciesSlotNumber_sqrt();
		if (maxSpeciesPerLine <= 0)	// FIXME : useful ?
			return;
		
		final int speciesWidth = tile.getWidth()/maxSpeciesPerLine;
		final int speciesHeight = tile.getHeight()/maxSpeciesPerLine;
		final int countHeight = speciesWidth/2;
	    g.setFont(new Font("Arial", Font.PLAIN, countHeight -1));
		
		int currentLine = 0;
		int currentRow = 0;
		int currentCount = 0;	// defined here to avoid defining an int at each loop 
		for (int i = 0; i < tile.getSpeciesNumber(); i++) {
			// Placement and value getting
			currentCount = tile.getSpeciesCount(i);
			int x = speciesWidth * currentRow;
			int y = speciesHeight * currentLine;
			
			// Line/Row management
			currentRow++;
			if (currentRow == maxSpeciesPerLine) {
				currentRow = 0;
				currentLine++;
			}
			
			// Proper display
			if (currentCount == 0)
				continue;
			
			Image speciesImage = tile.getOwner().getSpeciesImage(i + 1);	// +1 because the empty species isn't represent in the tile species
			if (speciesImage == null || speciesImage.getHeight(null) < 0) {
				g.setColor(tile.getOwner().getMissingSpeciesImageColor());
		        g.fillRoundRect(x, y, speciesWidth, speciesHeight, 0, 0);
	        }
			else
	        	g.drawImage(speciesImage, x, y, speciesWidth, speciesHeight, tile);
	        
	        if (currentCount == 1)
	        	continue;
	        
	        g.setColor(tile.getOwner().getSpeciesCountBGColor());
	        g.fillRoundRect(x, y, countHeight, countHeight, 0, 0);
		    g.setColor(Color.white);
		    g.drawString(Integer.toString(currentCount), x + 2, y + countHeight -1);
        }
	}
	
	/**
	* Paints the grid over the tile.
	* It's basically a rectangle around the tile.
	* Color and thickness are retrieved from the tile's owner (a WorldPanel)
	* 
	* @param graphics2D the Graphic Context to use
	* @param component the tile component to display
	* 
	* @see WorldPanel
	*/
	private void paintGrid(Graphics2D g, TileComponent tile) {
		g.setColor(tile.getOwner().getGridColor());
		g.setStroke(new BasicStroke(tile.getOwner().getGridThickness()));		
		g.drawRect(0, 0, tile.getWidth(), tile.getHeight());
	}
}
