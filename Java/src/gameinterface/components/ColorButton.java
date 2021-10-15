package gameinterface.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JFrame;

import gameinterface.ColorDialogBox;

/**
* A button that displays a color instead of the regular text/icon.
* When clicked, it will pop a ColorDialogBox to change the color displayed.
* 
* @see ColorDialogBox
*/ 
public class ColorButton extends JButton{
	private Color color;
	
	/**
	* @param color the initial color displayed
	*/
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
	
	/**
	* Pops a modal dialog box with a color wheel so the user can set the color he wants.
	* When the dialog box is closed by cliking the "OK" button, the color set by the user replaces the one displayed by the button.
	* @see ColorDialogBox
	*/ 
	private void askColor() {
		ColorDialogBox dialogBox = new ColorDialogBox((JFrame)this.getRootPane().getParent(), color);
		if (!dialogBox.getConfirm())
			return;
		
        color = dialogBox.getColor();
        repaint();
	}
	
	/**
	* @return the color of the button
	*/ 
	public Color getColor() {
		return color;
	}
	
	/**
	* @param color the color of the button
	*/ 
	public void setColor(Color color) {
		this.color = color;
		repaint();
	}
}
