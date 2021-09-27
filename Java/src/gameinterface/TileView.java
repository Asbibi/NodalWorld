package gameinterface;

import java.awt.*;

/**
* This class is responsible for the view part of the TileComponent
* 
* @see TileComponent
*/ 
public class TileView {

	/**
	* Display the tile entierly
	* 
	* @param The graphic context used to display
	* @param The model displayed
	*/ 
	public void paint(Graphics2D g, TileComponent tile) {
		paintSurface(g, tile);
		paintSpecies(g, tile);
		
		if (tile.getOwner().getDisplayGridDetail())
			paintGrid(g, tile);
	}
	
	
	/**
	* Display the surface of the tile
	* 
	* @param The graphic context used to display
	* @param The model displayed
	*/
	private void paintSurface(Graphics2D g, TileComponent tile) {
		if (tile.isSurfaceEmpty()) {
			g.setColor(tile.getOwner().getEmptyColor());
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
	* Display the species on the tile
	* 
	* @param The graphic context used to display
	* @param The model displayed
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
		int currentCount = 0;	// defined here to avoid defining as int at each loop 
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
			
			//System.out.println(tile.tileId + " - Line : " + currentLine + " | Col : " + currentRow + " | Species : " + i + "" + tile.getOwner().speciesDisplayed.get(i).toString() + " | Count : " + currentCount);
	        Image speciesImage = tile.getOwner().getSpeciesImage(i);
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
	* Display the grid over the tile.
	* It's basically a rectangle around the tile.
	* Color and thickness are retrieved from the tile's owner 
	* 
	* @param The graphic context used to display
	* @param The model displayed
	* @see WorldPanel
	*/
	private void paintGrid(Graphics2D g, TileComponent tile) {
		int gridHalfSize = tile.getOwner().getGridThickness();	// is the half of the effective grid thickness as both tiles will draw it
															// a workaround for this issue would be to only display grid on the top and left edges, but it's a minor issue
		g.setColor(tile.getOwner().getGridColor());
		
		g.fillRoundRect(0, 0, tile.getWidth(), gridHalfSize, 0, 0);
		g.fillRoundRect(0, tile.getHeight() - gridHalfSize, tile.getWidth(), gridHalfSize, 0, 0);
		g.fillRoundRect(0, 0, gridHalfSize, tile.getHeight(), 0, 0);
		g.fillRoundRect(tile.getWidth() - gridHalfSize, 0, gridHalfSize, tile.getHeight(), 0, 0);
	}
}
