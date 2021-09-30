package gameinterface.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ColorButton extends JButton{

	private Color color;
	
	public ColorButton(Color color) {
		this.color = color;
		addActionListener( e -> askColor() );
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		g2D.setColor(color);
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.setColor(Color.black);
		g2D.drawRect(0, 0, getWidth(), getHeight());
	}
	
	private void askColor() {
		String m = JOptionPane.showInputDialog("Input a hex color", "0x");		
        color = Color.decode(m);
        repaint();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		repaint();
	}
}
