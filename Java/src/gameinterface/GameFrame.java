package gameinterface;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;

import javax.swing.*;

import gamelogic.GameManager;
import gamelogic.Saver;

/**
* This class manages the display of the whole game in its own window
* 
* @see GameManager
* @see ControlPanel
* @see WorldPanel
*/ 
public class GameFrame extends JFrame {
	static private Color 	separatorColor = new Color(180,180,180);
	
	private	WorldPanel 		worldPanel;
	private	ControlPanel 	controlPanel;
	
	private JToggleButton	pauseButton;
	private JButton			slowButton;
	private JButton			speedButton;
	private JToggleButton	gridButton;
	private JToggleButton	detailButton;
	private JButton			zoomPlusButton;
	private JButton			zoomMinusButton;
	private JButton			zoomResetButton;
	private JButton			saveButton;
	private JButton			newButton;
	
	private JFileChooser 	savefileChooser;

	
	/**
	* Calls setupUI() and the Control & World panels' constructors methods
	*/ 
	public GameFrame(GameManager gameManager) {
		super("Nodal World");
		worldPanel = new WorldPanel(gameManager);
		controlPanel = new ControlPanel(gameManager);
		setupUI(gameManager);
		
		savefileChooser = new JFileChooser();
		savefileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		savefileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter savefileFilter = new FileNameExtensionFilter("Nodal World Save File (.nws)", "nws");
		savefileChooser.addChoosableFileFilter(savefileFilter);
		
		// === TO UNCOMENT WHEN SAVE WORKS ===
		/* addWindowListener(new WindowAdapter() {
	      	public void windowClosing(WindowEvent we) {
		        int result = JOptionPane.showConfirmDialog(frame,
		            "Do you want to Save ?", "Save",
		            JOptionPane.YES_NO_OPTION);
		        if (result == JOptionPane.YES_OPTION) {
		        	// ... save dialog box + save game
		          	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        }
		        else if (result == JOptionPane.NO_OPTION)
		          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        else
		          frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		      }
		  });
		 */
		
		updateWorld(0);
		worldPanel.resetTileSizeToMinimal();

		gameManager.addGameListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int frame = gameManager.getFrame();
				updateWorld(frame);
			}
		});
		setVisible(true);
	}
	
	/**
	* Sets up the UI Layout :
	* - Creates the menubar
	* - Separate the empty space into 2 areas of the same size (GridLayout) : one for the ControlPanel and one for the WorldPanel 
	*/
	private void setupUI(GameManager gameManager) {
        setPreferredSize(new Dimension(1280, 720));
        
        setupMenuBar(gameManager);
        
        JPanel worldParentPanel = new JPanel() { @Override public void setSize(Dimension d) { super.setSize(d); worldPanel.computeMinimalTileSize(); }};
        worldParentPanel.setLayout(new FlowLayout());
        worldParentPanel.setBorder( BorderFactory.createEmptyBorder(-5, -5, -5, -5) );
        worldParentPanel.setBackground(Color.darkGray);
        worldParentPanel.add(worldPanel);
		JSplitPane splitPanel = new JSplitPane(SwingConstants.VERTICAL, controlPanel, new JScrollPane(worldParentPanel));
		add(splitPanel);

        pack();
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    splitPanel.setDividerLocation(0.5);	// after pack() to let the split panel get its definitive size before setting the divider to the center (otherwise it's setting itself to 0)
	}
	/**
	* Creates the menubar of the game
	*/ 
	private void setupMenuBar(GameManager gameManager) {
		JMenuBar menuBar = new JMenuBar();
		Dimension buttonDimension = new Dimension(110,25);
		
		ImageIcon pauseIcon = new ImageIcon("res/_System_PlayPause.png");
		ImageIcon slowIcon = new ImageIcon("res/_System_SlowPlay.png");
		ImageIcon fastIcon = new ImageIcon("res/_System_FastPlay.png");
		pauseButton = new JToggleButton("Pause",pauseIcon);
		slowButton = new JButton("Slow",slowIcon);
		speedButton = new JButton("Faster",fastIcon);
		pauseButton.setPreferredSize(buttonDimension);
		slowButton.setPreferredSize(buttonDimension);
		speedButton.setPreferredSize(buttonDimension);
		
		ImageIcon gridIcon = new ImageIcon("res/_System_Grid.png");
		ImageIcon eyeIcon = new ImageIcon("res/_System_Eye.png");
		ImageIcon zoomPIcon = new ImageIcon("res/_System_ZoomPlus.png");
		ImageIcon zoomMIcon = new ImageIcon("res/_System_ZoomMinus.png");
		ImageIcon zoomRIcon = new ImageIcon("res/_System_Zoom.png");
		gridButton = new JToggleButton("Grid", gridIcon);
		detailButton = new JToggleButton("Simple", eyeIcon);
		zoomPlusButton = new JButton("Zoom +",zoomPIcon);
		zoomMinusButton = new JButton("Zoom -",zoomMIcon);
		zoomResetButton= new JButton("Reset",zoomRIcon);
		gridButton.setPreferredSize(buttonDimension);
		detailButton.setPreferredSize(buttonDimension);
		zoomPlusButton.setPreferredSize(buttonDimension);
		zoomMinusButton.setPreferredSize(buttonDimension);
		zoomResetButton.setPreferredSize(buttonDimension);
		gridButton.addActionListener(e -> worldPanel.flipDisplayGridDetail() );
		detailButton.addActionListener(e -> worldPanel.flipUseColorOverImage() );
		zoomPlusButton.addActionListener(e -> worldPanel.increaseTileSize() );
		zoomMinusButton.addActionListener(e -> worldPanel.decreaseTileSize() );
		zoomResetButton.addActionListener(e -> worldPanel.resetTileSizeToMinimal() );
		
		ImageIcon saveIcon = new ImageIcon("res/_System_Save.png");
		ImageIcon newIcon = new ImageIcon("res/_System_New.png");
		saveButton = new JButton("Save",saveIcon);
		newButton = new JButton("New",newIcon);
		saveButton.setPreferredSize(buttonDimension);
		newButton.setPreferredSize(buttonDimension);
		saveButton.addActionListener( e -> saveGameManager(gameManager) );
		
		// =============== ADD TIME =================
		
		menuBar.add(pauseButton);
		menuBar.add(slowButton);
		menuBar.add(speedButton);
		
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		
		menuBar.add(gridButton);
		menuBar.add(detailButton);	
		menuBar.add(zoomPlusButton);
		menuBar.add(zoomMinusButton);
		menuBar.add(zoomResetButton);
		
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		
		menuBar.add(saveButton);
		menuBar.add(newButton);
	    setJMenuBar(menuBar);
	}
	
	/**
	* Ask the world panel to updates itself
	* @param the last frame processed
	*/
	public void updateWorld(int frame) {
		worldPanel.updateMap(frame);
	}
	
	/**
	* @param listener the ActionListener to add to the pause button
	*/
	public void addPlayPauseListener(ItemListener listener) {
		pauseButton.addItemListener(listener);
	}
	/**
	* @param listener the ActionListener to add to the slow down button
	*/
	public void addSlowDownListener(ActionListener listener) {
		slowButton.addActionListener(listener);
	}
	/**
	* @param listener the ActionListener to add to the speed up button
	*/
	public void addSpeedUpListener(ActionListener listener) {
		speedButton.addActionListener(listener);
	}
	
	/**
	* @param the ActionListener to add to the new/load button
	*/
	public void addNew_LoadActionListener(ActionListener action) {
		newButton.addActionListener(action);
	}

	
	
	
	
	/**
	* @param the GameManager to save
	*/
	public void saveGameManager(GameManager gameManager) {
        int res = savefileChooser.showSaveDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
   			Saver.saveGame(savefileChooser.getSelectedFile().getPath(), gameManager, true);	//"/savetest"
        }
	}
	

	
	
	
	
	/**
	* @return the color the separator should use in the interface
	*/
	static public Color getSeparatorColor() { return separatorColor; }
}


/* TODO :
- find a way to ask to update world if element image changed		=>		listener
------//----
- saving on exit
- button load to restart
------//----
- debug element list : if clicked then drag then release on another element, for JList it will be the selected one but not for the detail panel
*/