package gameinterface.components;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class TerrainStackVisualizerVectors extends JComponent {
	private TerrainStackVisualizer model;
	int lenght = 2;
	double arrowSize = 0.5;
	int offset_x = 2;
	int offset_y = 4;
	
	public TerrainStackVisualizerVectors(TerrainStackVisualizer model) {
		this.model = model;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;		
		
		int d_x = model.getDelta_x();
		int d_y = model.getDelta_y();
		int sizeDelta_x = d_x*lenght;
		int sizeDelta_y = d_y*lenght;
		
		
		/* ======= 	Geometry - kept the point positions before simplification for potential debug purposes 	=======
		
		int[] arrow1_x = {border + sizeDelta_x + d_x,		border - d_x * (arrowSize + arrowSize) + sizeDelta_x,		border + d_x * (arrowSize - arrowSize) + sizeDelta_x};
		int[] arrow1_y = {border + sizeDelta_y*2 + d_y,		border + d_y * (arrowSize - arrowSize) + sizeDelta_y*2,		border - d_y * (arrowSize + arrowSize) + sizeDelta_y*2};

		int[] arrow2_x = {border + sizeDelta_x + d_x,		border + d_x * (arrowSize - arrowSize) + sizeDelta_x,		border - d_x * (arrowSize + arrowSize) + sizeDelta_x};
		int[] arrow2_y = {border - d_y,						border + d_y * (arrowSize + arrowSize), 					border + d_y * (arrowSize - arrowSize)};*/
		
		
		
		int[] arrow1_x = {sizeDelta_x + d_x + offset_x,			sizeDelta_x + offset_x,											(int)(sizeDelta_x - d_x * 2 * arrowSize) + offset_x};
		int[] arrow1_y = {sizeDelta_y*2 + 2*d_y + offset_y,		(int)(sizeDelta_y*2 + d_y * ( 1 - 2 * arrowSize)) + offset_y,	sizeDelta_y*2 + d_y + offset_y};

		int[] arrow2_x = {sizeDelta_x + d_x + offset_x,			sizeDelta_x + offset_x,											(int)(sizeDelta_x - d_x * 2 * arrowSize) + offset_x};
		int[] arrow2_y = {offset_y,								(int)(d_y * (1 + 2* arrowSize)) + offset_y, 					d_y + offset_y};
		
		g2d.setColor(TerrainStackVisualizer.getRealFocusedColor());
		g2d.setStroke(new BasicStroke(2));
		
		g2d.drawLine(offset_x, offset_y + d_y + sizeDelta_y, offset_x + sizeDelta_x, offset_y + d_y);
		g2d.drawLine(offset_x, offset_y + d_y + sizeDelta_y, offset_x + sizeDelta_x, offset_y + d_y + sizeDelta_y*2);
		g2d.fillPolygon(arrow1_x,arrow1_y,3);
		g2d.fillPolygon(arrow2_x,arrow2_y,3);
		

	    g.setFont(new Font("Arial", Font.PLAIN, 5 * lenght));
	    g.drawString("X", offset_x-2, offset_y + d_y + sizeDelta_y - 2 *(lenght));
	    g.drawString("Y", offset_x-2, offset_y + d_y + sizeDelta_y + 6 * lenght + 1);
	}
	
	@Override
	public Dimension getPreferredSize() {
		int d_x = model.getDelta_x();
		int d_y = model.getDelta_y();
		return new Dimension(d_x * (lenght + 1) + offset_x * 2, d_y * (lenght + 1) * 2 + offset_y * 2);
	}
}
