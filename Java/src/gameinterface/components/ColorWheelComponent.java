package gameinterface.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;


public class ColorWheelComponent extends JComponent {
	private ColorWheelView view;
	private BufferedImage wheelImage;
	private double value;
	private double x_Cursor;
	private double y_Cursor;
	
	public ColorWheelComponent(Color color) {
		view = new ColorWheelView();
		setColorToCursor(color);				
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setCursorPositionFromPoint(e.getPoint());
				repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		generateWheelImage(getHeight(), false);
		view.paint((Graphics2D)g, this);
	}
	public int getSliderWidth() {
		return Math.max(getHeight()/20, 5);
	}
	public BufferedImage getWheelImage() { return wheelImage; }
	public int getCursorX() { return (int)(x_Cursor * getHeight()); }
	public int getCursorY() { return (int)(y_Cursor * getHeight()); }
	public int getValueCursorY() { return (int)((1-value) * getHeight()); }
	public int getXOffset() {
		return (getWidth() - getHeight() - 5 - getSliderWidth())/2;
	}
	public double getValue() { return value; }
	
	private void generateWheelImage(int diameter, boolean forceRegenerate) {
		if (!forceRegenerate && wheelImage!=null && diameter == wheelImage.getHeight())
			return;
		
		int sliderWidth = getSliderWidth() + 5;
		wheelImage = new BufferedImage(diameter + sliderWidth, diameter, BufferedImage.TYPE_4BYTE_ABGR);
		
		int radius = diameter/2;
		
		for (int x = -radius; x < radius; x++) {
			for (int y = -radius; y < radius; y++) {
				double curRadius = Math.sqrt(x*x + y*y);
				if (curRadius > radius)
					wheelImage.setRGB(x + radius, y + radius, 0x00000000);
				else {
					double saturation = curRadius/radius;
					double hue = Math.toDegrees(Math.atan2(y, x));
														
					wheelImage.setRGB(x + radius, y + radius, convertHSVtoRGB(hue, saturation, value));
				}
			}
		}
		
		for (int y = 0; y < diameter; y++) {
			int y_Value = (int)(255 * (1 - (double)y/diameter));
			int rgb = (255 << 24) | (y_Value << 16) | (y_Value << 8) | y_Value;
			for (int x = diameter; x < diameter + 5; x++) {
				wheelImage.setRGB(x, y, 0);
			}
			for (int x = diameter + 5; x < diameter + sliderWidth; x++) {
				wheelImage.setRGB(x, y, rgb);
			}
		}
	}
	public void setCursorPositionFromPoint(Point point) {
		double diameter = getHeight();
		double x_pos = (point.x - getXOffset()) / diameter;
		double y_pos = point.y / diameter;
		
		if (x_pos > 1) {		// value slider
			value = Math.min(Math.max(1-y_pos,0),1);
			generateWheelImage(getHeight(), true);
			
		} else {				// HUE-Saturation wheel
			double vect_norm = Math.sqrt(Math.pow(x_pos-0.5,2) + Math.pow(y_pos-0.5,2));
			if (vect_norm > 0.5)
				return;
			
			x_Cursor = x_pos;
			y_Cursor = y_pos;
		}
	}
	public Color getColorFromCursor() {
		double x = x_Cursor*2 -1; 
		double y = y_Cursor*2 -1; 
		double saturation = Math.sqrt(x*x + y*y);
		double hue = Math.toDegrees(Math.atan2(y, x));
		return new Color(convertHSVtoRGB(hue, saturation, value));
	}
	public void setColorToCursor(Color color) {
		float[] hsv = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(),hsv);
		value = hsv[2];
		double hue = hsv[0] * 2 * Math.PI;
		x_Cursor = hsv[1] * Math.cos(hue);
		y_Cursor = hsv[1] * Math.sin(hue);
		x_Cursor = x_Cursor/2 + 0.5;
		y_Cursor = y_Cursor/2 + 0.5;
	}
	private int convertHSVtoRGB(double hue, double saturation, double value) {
		if (hue < 0) hue += 360;
		double C = saturation * value;
		double X = C * (1 - Math.abs(((hue/60)%2) - 1));
		double m = value - C;
		double r = 0;
		double g = 0;
		double b = 0;
		
		// [0 ...
		 if (hue < 60) {
			r = C;
			g = X;
		} else if (hue < 120) {
			r = X;
			g = C;
		} else if (hue < 180) {
			g = C;
			b = X;
		} else if (hue < 240) {
			g = X;
			b = C;
		} else if (hue < 300) {
			r = X;
			b = C;
		} else if (hue < 360) {
			r = C;
			b = X;
		}
		
		int R =(int)((r+m)*255);
		int G =(int)((g+m)*255);
		int B =(int)((b+m)*255);					
		
		return (255 << 24) | (R << 16) | (G << 8) | B;
	}
}
