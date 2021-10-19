package gameinterface;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;

import javax.swing.*;

import gamelogic.GameManager;
import gamelogic.GameManagerBuilder;
import gamelogic.Saver;

/**
* The game's window.<br/>
* It's basically composed of a MenuBar on top, a ControlPanel on the left side and a WorldPanel on the right side. 
* 
* @see GameManager
* @see ControlPanel
* @see WorldPanel
*/ 
public class GameFrame extends JFrame {
	static private Color 	standardFieldColor = Color.white;
	static private Color 	wrongFieldColor = Color.red;
	static private Color 	separatorColor = new Color(180,180,180);
	
	private	WorldPanel 		worldPanel;
	private	ControlPanel 	controlPanel;
	
	private JToggleButton	pauseButton;
	private JLabel			frameCount;
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
	private ArrayList<ActionListener> onLoadListener;
	private ImageIcon 		pauseIcon;
	private ImageIcon 		playIcon;

	
	/**
	* Calls setupUI() and the Control & World panels' constructors methods.
	* @param gameManager the gameManager the gameFrame represents
	*/ 
	public GameFrame(GameManager gameManager) {
		super("Nodal World");
		worldPanel = new WorldPanel(gameManager);
		controlPanel = new ControlPanel(gameManager);
		setupUI();
		connectGameManager(gameManager);
		
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
		
		updateFrame(0);
		worldPanel.resetTileSizeToMinimal();

		setIconImage((new ImageIcon("res/_System_App_Icon.png")).getImage());
		
		setVisible(true);
	}
	
	/**
	* Sets up the UI Layout:<br/>
	* - Creates the menubar<br/>
	* - Separate the main space into 2 areas of the same default size (resizable) : the left one for the ControlPanel and the right one for the WorldPanel 
	*/
	private void setupUI() {
        setPreferredSize(new Dimension(1280, 720));
        
        setupMenuBar();
        
        JPanel worldParentPanel = new JPanel() {
        	@Override public void setSize(Dimension d) { super.setSize(d); worldPanel.computeMinimalTileSize(); }};
        worldParentPanel.setLayout(new FlowLayout());
        worldParentPanel.setBorder( BorderFactory.createEmptyBorder(-5, -5, -5, -5) );
        worldParentPanel.setBackground(Color.gray);
        worldParentPanel.add(worldPanel);
		JSplitPane splitPanel = new JSplitPane(SwingConstants.VERTICAL, controlPanel, new JScrollPane(worldParentPanel));
		add(splitPanel);

        pack();
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    splitPanel.setDividerLocation(0.5);	// after pack() to let the split panel get its definitive size before setting the divider to the center (otherwise it's setting itself to 0)
	}
	
	/**
	* Creates the menubar of the GameFrame.
	*/ 
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		Dimension buttonDimension = new Dimension(110,25);
		
		pauseIcon = new ImageIcon("res/_System_Pause.png");
		playIcon = new ImageIcon("res/_System_Play.png");
		ImageIcon slowIcon = new ImageIcon("res/_System_SlowPlay.png");
		ImageIcon fastIcon = new ImageIcon("res/_System_FastPlay.png");
		pauseButton = new JToggleButton("Play",playIcon);
		frameCount = new JLabel("Time: 0", JLabel.CENTER);
		slowButton = new JButton("Slow",slowIcon);
		speedButton = new JButton("Faster",fastIcon);
		pauseButton.setPreferredSize(buttonDimension);
		frameCount.setPreferredSize(buttonDimension);
		slowButton.setPreferredSize(buttonDimension);
		speedButton.setPreferredSize(buttonDimension);
		pauseButton.addActionListener(e -> pauseButton.setText(pauseButton.isSelected() ? "Pause" : "Play") );
		pauseButton.addActionListener(e -> pauseButton.setIcon(pauseButton.isSelected() ? pauseIcon : playIcon) );
		
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
		newButton.addActionListener( e -> startNew_LoadedGame() );
		onLoadListener = new ArrayList<ActionListener>();
		addNew_LoadActionListener(e -> connectGameManager(((LoadEvent)e).getLoadedManager()));
		addNew_LoadActionListener(e -> updateFrame(0));
		
		
		// =============== ADD BUTTONS =================
		
		menuBar.add(pauseButton);
		menuBar.add(frameCount);
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
	* Reconnect the GameFrame to a GameManager (used on loading).
	* @param gameManager the GameManager to connect the gameFrame to
	*/ 
	private void connectGameManager(GameManager gameManager) {
		if (gameManager == null)
			return;
		
		// Save Button
		for (ActionListener l : saveButton.getActionListeners())
			saveButton.removeActionListener(l);
		saveButton.addActionListener( e -> saveGameManager(gameManager) );
		
		// Update display on game evolve
		gameManager.addGameListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int frame = gameManager.getFrame();
				updateFrame(frame);
			}
		});
		
		// World Connection
		worldPanel.connectGameManager(gameManager);
		// Control Panel Connection
		controlPanel.connectGameManager(gameManager);
	}
	
	/**
	* Updates the frame display, usually called after the gameManager evolved.
	* @param frame the last frame processed, to check which species were triggered
	*/
	public void updateFrame(int frame) {
		worldPanel.updateMap(frame);
		controlPanel.update();
		frameCount.setText("Time: " + frame);
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
	* This list of action listeners is called after a load is performed, and they are triggered with a LoadEvent 
	* @param listener the ActionListener to add to the new/load action listener list
	*/
	public void addNew_LoadActionListener(ActionListener listener) {
		onLoadListener.add(listener);
	}

	
	
	
	
	/**
	* @param gameManager the GameManager to save
	*/
	private void saveGameManager(GameManager gameManager) {
        int res = savefileChooser.showSaveDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
        	String[] options = {"Pack images (Recommended)", "Use absolute paths"};        	
        	int resPack = JOptionPane.showOptionDialog(this,"Do you want to pack the images in the save file ?\n\nPacking the images in the save file makes it larger in memory.\nBut it will also make it independent of the image files used.","Pack images ?", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
			if(resPack == JOptionPane.YES_OPTION)
				Saver.saveGame(savefileChooser.getSelectedFile().getPath(), gameManager, true);
			else if (resPack == JOptionPane.NO_OPTION)
				Saver.saveGame(savefileChooser.getSelectedFile().getPath(), gameManager, false);
        }
	}
	
	/**
	* Creates a new GameManager based on the template selected by the user in the dialog box this method will open (which is the same dialog box than when the app starts).
	*/
	private void startNew_LoadedGame() {
		NewWorldDialogBox dialogBox = new NewWorldDialogBox(this);
		if (!dialogBox.getConfirm())
			return;
		
		GameManager newGamemanager = GameManagerBuilder.buildGameFromTemplate(dialogBox.getSelectedTemplate(), dialogBox.getTemplateWidth(), dialogBox.getTemplateHeight(), dialogBox.getSelectedSaveFilePath());
	
		if (newGamemanager != null)
			for (ActionListener loadListener : onLoadListener)
				loadListener.actionPerformed(new LoadEvent(this, newGamemanager));
	}
	

	
	
	
	
	/**
	* @return the color the separators should use in the interface
	*/
	static public Color getSeparatorColor() { return separatorColor; }
	/**
	* @return the color to use on a TextField background by default
	*/ 
	public static Color getStandardFieldColor() { return standardFieldColor; }
	/**
	* @return the color to use on a TextField background when the string inputed doesn't comply with constraints (e.g. writting letters in a TextField used for an int input)
	*/
	public static Color getWrongFieldColor() { return wrongFieldColor; }

}


/* TODO :
- find a way to ask to update world if element image changed		=>		listener
- debug element list : if clicked then drag then release on another element, for JList it will be the selected one but not for the detail panel
- stops save/load if folder/file exists with savefile name
- terrain visualizer is a bit laggy on focus
////////////////////////////////////////////////
- saving on exit ?
- change terrain dimension ?
- terrain grid paintable
- island templates
 */