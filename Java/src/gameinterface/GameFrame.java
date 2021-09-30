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
	public GameFrame(GameManager gameManager) {
		super("Nodal World");
		worldPanel = new WorldPanel(gameManager);
		controlPanel = new ControlPanel(gameManager);
		setupUI();
		updateInterface(0);
	}
	
	/**
	* Sets up the UI Layout :
	* - Creates the menubar
	* - Separate the empty space into 2 areas of the same size (GridLayout) : one for the ControlPanel and one for the WorldPanel 
	*/ 
	private void setupUI() {
        setPreferredSize(new Dimension(1280, 720));
        
        setupMenuBar();
        
        setLayout(new BorderLayout());
		JSplitPane splitPanel = new JSplitPane(SwingConstants.VERTICAL, controlPanel, new JScrollPane(worldPanel));
		add(splitPanel);

        pack();
        setVisible(true);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	    splitPanel.setDividerLocation(0.5);	// at the end to let the split panel get it's definitive size before setting the divider to the center
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
		detailButton.addActionListener(e -> worldPanel.flipDisplayGridDetail() );
		restartButton = new JButton("Delete & Restart a world");
		menuBar.add(pauseButton);
		menuBar.add(slowButton);
		menuBar.add(speedButton);
		menuBar.add(detailButton);
		menuBar.add(restartButton);
	    setJMenuBar(menuBar);
	}
	
	public void updateInterface(int frame) {
		worldPanel.update(frame);
	}

	
	
	// ===== TEST ONLY =====
	public WorldPanel gWP_test() { return worldPanel;}
	public ControlPanel gCTRL_test() { return controlPanel;}
}
