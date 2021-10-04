package gameinterface.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import gamelogic.Surface;
import gamelogic.TerrainLayer;
import gamelogic.Vec2D;

public class TerrainStackVisualizerView {
	

	public void paint(Graphics2D g2d, TerrainStackVisualizer model) {
		if (model.getStack() == null || model.getStack().isEmpty())
			return;
		
		int numberOfLayers = model.getStack().size();
		int offset_y = model.getOffset_y();
		final Integer delta_y = model.getDelta_y();
		final Integer delta_x = model.getDelta_x();
		int y = model.getOffset_Starting_y();
		
		boolean focus = !model.getOnlyFocusedInColor();
		
		for (int i = numberOfLayers -1; i > -1; i--) {
			y -= offset_y;
			if (i == model.getFocusedLayer()) {
				focus = true;
				paintOneLayer(g2d, model.getStack().get(i), delta_x, y, delta_x, delta_y, focus);
				paintBorderLayer(g2d, model.getStack().get(i), delta_x, y, delta_x, delta_y);
				y -= model.getOffset_Focus_y();
				focus = false;
			}
			else
				paintOneLayer(g2d, model.getStack().get(i), delta_x, y, delta_x, delta_y, focus);
		}
	}
	
	// layer paint scale
	private void paintOneLayer(Graphics2D g2d, TerrainLayer layer, int x_firstTile, int y_firstTile, final Integer delta_x, final Integer delta_y, boolean focus) {
		int w =layer.getWidth();
		int h =layer.getHeight();
		
		for (int x = 0; x < w; x++)		
			for (int y = 0; y < h; y++) {
				Surface surface = layer.surfaceAt(new Vec2D(x,y));
				if (surface == null || surface ==Surface.getEmpty())
					paintEmptySurface(g2d, x_firstTile + x*delta_x + y*delta_x, y_firstTile - x*delta_y + y*delta_y, delta_x, delta_y, focus);
				else
					paintSurface(g2d, focus ? surface.getColor() : TerrainStackVisualizer.getUnfocusedColor(), x_firstTile + x*delta_x + y*delta_x, y_firstTile - x*delta_y + y*delta_y, delta_x, delta_y);
			}		
	}
	private void paintBorderLayer(Graphics2D g2d, TerrainLayer layer, int x_firstTile, int y_firstTile, final Integer delta_x, final Integer delta_y) {
		int w =layer.getWidth();
		int h =layer.getHeight();
				
		int x = x_firstTile;
		int y = y_firstTile;
		
		int[] xs = {x-delta_x,	x + delta_x*(h-1),	x + delta_x*(h-1) + delta_x*(w),	x + delta_x*(w-1)};
		int[] ys = {y,			y + delta_y*h,		y + delta_y*h - delta_y*w,			y - delta_y*w};
		g2d.setColor(TerrainStackVisualizer.getRealFocusedColor());
		g2d.setStroke(new BasicStroke(2));
		g2d.drawPolygon(xs, ys, 4);
		g2d.setStroke(new BasicStroke(1));
	}
	
	// tile paint sacle
	private void paintSurface(Graphics2D g2d, Color color, int x, int y, final Integer delta_x, final Integer delta_y) {
		int[] xs = {x,			x-delta_x,	x,			x+delta_x};
		int[] ys = {y-delta_y,	y,			y+delta_y,	y};
		g2d.setColor(color);
		g2d.fillPolygon(xs, ys, 4);
	}
	private void paintEmptySurface(Graphics2D g2d, int x, int y, final Integer delta_x, final Integer delta_y, boolean focused) {
		int[] xs = {x,			x-delta_x,	x,			x+delta_x};
		int[] ys = {y-delta_y,	y,			y+delta_y,	y};
		g2d.setColor(focused ? Color.darkGray : TerrainStackVisualizer.getUnfocusedColor());
		g2d.drawPolygon(xs, ys, 4);
	}
}
