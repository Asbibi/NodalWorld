package gameinterface;

import java.awt.*;
import javax.swing.*;

import gamelogic.GameManager;

/**
* This class manages the display of the whole game in its own window
* 
* @see ControlPanel, WorldPanel
*/ 
public class GameFrame extends JFrame {
	private	WorldPanel 		worldPanel;
	private	ControlPanel 	controlPanel;
	
	private JButton			pauseButton;
	private JButton			slowButton;
	private JButton			speedButton;
	private JButton			detailButton;
	private JButton			restartButton;
	
	
	/**
	* Calls setupUI() and the Control & World panels' constructors methods
	*/ 
	public GameFrame() {
		super("Nodal World");
		worldPanel = new WorldPanel();
		controlPanel = new ControlPanel();
		setupUI();
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	* Sets up the UI Layout :
	* - Creates the menubar
	* - Separate the empty space into 2 areas of the same size (GridLayout) : one for the ControlPanel and one for the WorldPanel 
	*/ 
	private void setupUI() {
        setPreferredSize(new Dimension(1280, 720));
        setLayout(new GridLayout(0,2));
        
        setupMenuBar();
        
        add(controlPanel);
        add(new JScrollPane(worldPanel));

        pack();        
	}
	/**
	* Creates the menubar of the game
	*/ 
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		pauseButton = new JButton("Pause");
		slowButton = new JButton("Slow Down");
		speedButton = new JButton("Speed Up");
		detailButton = new JButton("Show Details");
		restartButton = new JButton("Delete & Restart a world");
		menuBar.add(pauseButton);
		menuBar.add(slowButton);
		menuBar.add(speedButton);
		menuBar.add(detailButton);
		menuBar.add(restartButton);
	    setJMenuBar(menuBar);
	}
	
	/**
	* Updates the world panel using the gameManager data
	* WIP
	*/ 
	public void update(GameManager gameManager)
	{
		//TODO
	}
	public WorldPanel gWP_test() { return worldPanel;}
	public ControlPanel gCTRL_test() { return controlPanel;}
}
