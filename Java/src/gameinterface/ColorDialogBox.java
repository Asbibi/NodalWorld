package gameinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gameinterface.components.ColorWheelComponent;

/**
* A modal dialog box that pops to ask the user to input a color using a color wheel
* 
* @see ColorWheelComponent
*/ 
public class ColorDialogBox extends JDialog{
	private boolean confirm = false;
	private ColorWheelComponent colorWheel;
	private JButton OKButton;
	private JButton CancelButton;
	
	public ColorDialogBox(JFrame owner, Color color) {
		super(owner, "Select a color", true);
		setLayout(new BorderLayout());
		colorWheel = new ColorWheelComponent(color);
		colorWheel.setPreferredSize(new Dimension(220,200));
		add(colorWheel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		OKButton = new JButton("OK");
		CancelButton = new JButton("Cancel");
		OKButton.addActionListener( e -> {confirm = true; dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));} );
		CancelButton.addActionListener( e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)) );
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(OKButton);
		buttonPanel.add(CancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	/**
	* Should be called once the dialog box is closed, and only if getConfirm() returns true 
	* @return the color inputed by the user
	*/ 
	public Color getColor() {
		return colorWheel.getColorFromCursor();
	}

	/**
	* @return if the user closed the dialog box by cliking on the "OK" button or not
	*/ 
	public boolean getConfirm() {
		return confirm;
	}
}
