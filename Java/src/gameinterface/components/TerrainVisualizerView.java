package gameinterface.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import gamelogic.Surface;
import gamelogic.TerrainSlot;
import gamelogic.Vec2D;

/**
* View of the TerrainVisualizerComponent
* 
* @see TerrainVisualizerComponent
*/
public class TerrainVisualizerView {
	
	/**
	* @param graphics2D the Graphic Context to use
	* @param component the terrain visualizer model/controller to display
	*/ 
	public void paint(Graphics2D g2d, TerrainVisualizerComponent model) {
		if (model.getTerrain().getSlots().isEmpty())
			return;
		
		int numberOfLayers = model.getTerrain().getSlots().size();
		int offset_y = model.getOffset_y();
		final Integer delta_y = model.getDelta_y();
		final Integer delta_x = model.getDelta_x();
		final Integer w = model.getTerrain().getWidth();
		final Integer h = model.getTerrain().getHeight();
		int y = model.getOffset_Starting_y();
		
		boolean focus = !model.getOnlyFocusedInColor();
		
		for (int i = numberOfLayers -1; i > -1; i--) {
			y -= offset_y;
			if (i == model.getFocusedLayer()) {
				focus = true;
				paintOneSlot(g2d, model.getTerrain().getSlot(i), w, h, delta_x, y, delta_x, delta_y, focus);
				paintBorderSlot(g2d, w, h, delta_x, y, delta_x, delta_y);
				//model.setFocusedLayerPositionOnParentScrollBar(y - model.getTerrain().getWidth() * delta_y + 0*delta_y);
				y -= model.getOffset_Focus_y();
				focus = false;
			}
			else
				paintOneSlot(g2d, model.getTerrain().getSlot(i), w, h, delta_x, y, delta_x, delta_y, focus);
		}
	}
	
	
	/**
	* The paint method for one layer.
	* @param graphics2D the Graphic Context to use
	* @param slot the TerrainSlot to paint
	* @param w_terrain the width of the terrain owning the slot
	* @param h_terrain the height of the terrain owning the slot
	* @param x_firstTilethe x position for the first tile of the layer
	* @param y_firstTile the y position for the first tile of the layer
	* @param delta_x the delta_x of the controller, the x distance to represent a tile side
	* @param delta_y the delta_y of the controller, the y distance to represent a tile side
	* @param focus indicates if slot is focused (i.e. if it's paint with its color or the unfocused gray colors)
	*/
	private void paintOneSlot(Graphics2D g2d, TerrainSlot slot, final Integer w,  final Integer h, int x_firstTile, int y_firstTile, final Integer delta_x, final Integer delta_y, boolean focus) {		
		for (int x = 0; x < w; x++)		
			for (int y = 0; y < h; y++) {
				Surface surface = slot.isOccupied() ? slot.getTerrainNode().getSurfaceAt(new Vec2D(x,y)) : null;
				if (surface == null || surface == Surface.getEmpty())
					paintEmptySurface(g2d, x_firstTile + x*delta_x + y*delta_x, y_firstTile - x*delta_y + y*delta_y, delta_x, delta_y, focus);
				else
					paintSurface(g2d, focus ? surface.getColor() : TerrainVisualizerComponent.getUnfocusedColor(), x_firstTile + x*delta_x + y*delta_x, y_firstTile - x*delta_y + y*delta_y, delta_x, delta_y);
			}
	}

	/**
	* The paint method for the border of a layer.
	* @param graphics2D the Graphic Context to use
	* @param w_terrain the width of the terrain owning the slot
	* @param h_terrain the height of the terrain owning the slot
	* @param x_firstTilethe x position for the first tile of the layer
	* @param y_firstTile the y position for the first tile of the layer
	* @param delta_x the delta_x of the controller, the x distance to represent a tile side
	* @param delta_y the delta_y of the controller, the y distance to represent a tile side
	*/
	private void paintBorderSlot(Graphics2D g2d, final Integer w,  final Integer h, int x_firstTile, int y_firstTile, final Integer delta_x, final Integer delta_y) {
		int x = x_firstTile;
		int y = y_firstTile;
		
		int[] xs = {x-delta_x,	x + delta_x*(h-1),	x + delta_x*(h-1) + delta_x*(w),	x + delta_x*(w-1)};
		int[] ys = {y,			y + delta_y*h,		y + delta_y*h - delta_y*w,			y - delta_y*w};
		g2d.setColor(TerrainVisualizerComponent.getRealFocusedColor());
		g2d.setStroke(new BasicStroke(2));
		g2d.drawPolygon(xs, ys, 4);
		g2d.setStroke(new BasicStroke(1));
	}
	

	/**
	* The paint method for a regular (non empty) tile : it's displayed as a losange filled with the tile's surface's color.
	* @param graphics2D the Graphic Context to use	
	* @param color the color of the tile (should be got from its Surface)
	* @param x the x position of the tile to paint
	* @param y the y position of the tile to paint
	* @param delta_x the delta_x of the controller, the x distance to represent a tile side
	* @param delta_y the delta_y of the controller, the y distance to represent a tile side
	*/
	private void paintSurface(Graphics2D g2d, Color color, int x, int y, final Integer delta_x, final Integer delta_y) {
		int[] xs = {x,			x-delta_x,	x,			x+delta_x};
		int[] ys = {y-delta_y,	y,			y+delta_y,	y};
		g2d.setColor(color);
		g2d.fillPolygon(xs, ys, 4);
	}
	/**
	* The paint method for a regular (non empty) tile : it's displayed as a losange filled with the tile's surface's color.
	* @param graphics2D the Graphic Context to use
	* @param x the x position of the tile to paint
	* @param y the y position of the tile to paint
	* @param delta_x the delta_x of the controller, the x distance to represent a tile side
	* @param delta_y the delta_y of the controller, the y distance to represent a tile side
	* @param focused indicates if the TerrainSlot the tile is from is the slot focused (i.e. if it's paint with its color or the unfocused gray colors)
	*/
	private void paintEmptySurface(Graphics2D g2d, int x, int y, final Integer delta_x, final Integer delta_y, boolean focused) {
		int[] xs = {x,			x-delta_x,	x,			x+delta_x};
		int[] ys = {y-delta_y,	y,			y+delta_y,	y};
		g2d.setColor(focused ? Color.darkGray : TerrainVisualizerComponent.getUnfocusedColor());
		g2d.drawPolygon(xs, ys, 4);
	}
}
