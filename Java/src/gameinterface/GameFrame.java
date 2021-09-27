package gameinterface;

import java.awt.*;
import javax.swing.*;

import gamelogic.GameManager;

public class GameFrame extends JFrame {
	private	WorldPanel 		worldPanel;
	private	ControlPanel 	controlPanel;
	
	private JButton			pauseButton;
	private JButton			slowButton;
	private JButton			speedButton;
	private JButton			detailButton;
	private JButton			restartButton;
	
	
	public GameFrame() {
		super("Nodal World");
		worldPanel = new WorldPanel();
		controlPanel = new ControlPanel();
		setupUI();
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void setupUI() {
        setPreferredSize(new Dimension(1280, 720));
        setLayout(new BorderLayout());
        
        setupMenuBar();
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.add(controlPanel);
        centerPanel.add(new JScrollPane(worldPanel));
        add(centerPanel, BorderLayout.CENTER);

        pack();        
	}
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
	
	
	public void update(GameManager gameManager)
	{
		//TODO
	}
	public WorldPanel gWP_test() { return worldPanel;}
	public ControlPanel gCTRL_test() { return controlPanel;}
}
