package gameinterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
* The dialog box that will pop to start a new world.<br/>
* This dialog box proposes 8 options to create a new world.<br/>
* Some of those options requires to give terrain dimensions, which can be done using the text fields at the bottom. They are disable if not needed.
* 
* @see NewWorldTemplate
*/ 
public class NewWorldDialogBox extends JDialog {

	private boolean confirm = false;
	private NewWorldTemplate template = NewWorldTemplate.empty;
	private int width = 10;
	private int height = 10;
	private JFileChooser savefileChooser;
	
	private JButton OKButton;
	private JButton CancelButton;
	private TextFieldPanel widthInputField;
	private TextFieldPanel heightInputField;
	
	private NewWorldTemplatePanel emptyPanel;
	private NewWorldTemplatePanel basicPanel;
	private NewWorldTemplatePanel islandPanel;
	private NewWorldTemplatePanel completeDemoPanel;
	private NewWorldTemplatePanel loadElementsPanel;
	private NewWorldTemplatePanel loadElementsTerrainPanel;
	private NewWorldTemplatePanel loadElementsAllNodesPanel;
	private NewWorldTemplatePanel loadFullSavePanel;
	
	
	/**
	* @param owner the JFrame hosting the dialog box 
	*/ 
	public NewWorldDialogBox(JFrame owner) {
		super(owner, "Choose a starting template", true);
		
		savefileChooser = new JFileChooser();
		savefileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		savefileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter savefileFilter = new FileNameExtensionFilter("Nodal World Save File (.nws)", "nws");
		savefileChooser.addChoosableFileFilter(savefileFilter);
		
		setLayout(new BorderLayout());
		
		JPanel templatePanel = new JPanel();
		templatePanel.setLayout(new GridLayout(2,4, 10, 30));
		templatePanel.add(emptyPanel = new NewWorldTemplatePanel(NewWorldTemplate.empty));
		templatePanel.add(basicPanel = new NewWorldTemplatePanel(NewWorldTemplate.basic));
		templatePanel.add(islandPanel = new NewWorldTemplatePanel(NewWorldTemplate.island));
		templatePanel.add(completeDemoPanel = new NewWorldTemplatePanel(NewWorldTemplate.completeDemo));
		templatePanel.add(loadElementsPanel = new NewWorldTemplatePanel(NewWorldTemplate.loadElements));
		templatePanel.add(loadElementsTerrainPanel = new NewWorldTemplatePanel(NewWorldTemplate.loadElementsTerrain));
		templatePanel.add(loadElementsAllNodesPanel = new NewWorldTemplatePanel(NewWorldTemplate.loadElementsAllNodes));
		templatePanel.add(loadFullSavePanel = new NewWorldTemplatePanel(NewWorldTemplate.loadFullSave));
		emptyPanel.addActionListener( e -> selectTemplatePanel(emptyPanel) );
		basicPanel.addActionListener( e -> selectTemplatePanel(basicPanel) );
		islandPanel.addActionListener( e -> selectTemplatePanel(islandPanel) );
		completeDemoPanel.addActionListener( e -> selectTemplatePanel(completeDemoPanel) );
		loadElementsPanel.addActionListener( e -> selectTemplatePanel(loadElementsPanel) );
		loadElementsTerrainPanel.addActionListener( e -> selectTemplatePanel(loadElementsTerrainPanel) );
		loadElementsAllNodesPanel.addActionListener( e -> selectTemplatePanel(loadElementsAllNodesPanel) );
		loadFullSavePanel.addActionListener( e -> selectTemplatePanel(loadFullSavePanel) );
		emptyPanel.setSelected(true);
		add(templatePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		OKButton = new JButton("OK");
		getRootPane().setDefaultButton(OKButton);
		OKButton.requestFocus();
		CancelButton = new JButton("Cancel");
		OKButton.addActionListener( e -> onOkButtonPressed() );
		CancelButton.addActionListener( e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)) );
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(OKButton);
		buttonPanel.add(CancelButton);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputPanel.add(widthInputField = new TextFieldPanel("Width:"));
		inputPanel.add(heightInputField = new TextFieldPanel("Height:"));
		widthInputField.setFieldString("25");
		heightInputField.setFieldString("25");
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(0,2));
		southPanel.add(new JLabel());
		southPanel.add(new JLabel());
		southPanel.add(inputPanel);
		southPanel.add(buttonPanel);
		add(southPanel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}

	/**
	* Callback for the Template Panel that is called when a template panel is clicked.<br/>
	* It will mark it as selected, deselect the others, and enable or disable the input fields for terrain dimension.
	* @param selectedTemplatePanel the templatePanel selected
	*/ 
	private void selectTemplatePanel(NewWorldTemplatePanel selectedTemplatePanel) {
		emptyPanel.setSelected(false);
		basicPanel.setSelected(false);
		islandPanel.setSelected(false);
		completeDemoPanel.setSelected(false);
		loadElementsPanel.setSelected(false);
		loadElementsTerrainPanel.setSelected(false);
		loadElementsAllNodesPanel.setSelected(false);
		loadFullSavePanel.setSelected(false);
		
		selectedTemplatePanel.setSelected(true);
		template = selectedTemplatePanel.getTemplate();
		widthInputField.setEnable(template.getAskDimension());
		heightInputField.setEnable(template.getAskDimension());
	}
	

	/**
	* Callback for OK button.<br/>
	* First of all it will check that if the template needs dimensions, the dimensions given are correct (ie the string input is readable as an int).<br/>
	* Then, if it's template based on loading a save file, it will open a file chooser to select a savefile.<br/>
	* If any of this steps fails, it simply return the method, otherwhise it will close the dialog box with the confirm boolean set to true.
	* 
	* @param selectedTemplatePanel the templatePanel selected
	*/ 
	private void onOkButtonPressed() {
		confirm = true;
		if (template.getAskDimension()) {
			try {
				width = Integer.parseInt(widthInputField.getFieldString());
				widthInputField.setFieldColor(GameFrame.getStandardFieldColor());
			} catch (Exception except) {
				widthInputField.setFieldColor(GameFrame.getWrongFieldColor());
				confirm = false;
			}
			try {
				height = Integer.parseInt(heightInputField.getFieldString());
				heightInputField.setFieldColor(GameFrame.getStandardFieldColor());
			} catch (Exception except) {
				heightInputField.setFieldColor(GameFrame.getWrongFieldColor());
				confirm = false;
			}
			if(!confirm)
				return;
		}
		
		if (template.getAskLoading()) {
			int result = savefileChooser.showOpenDialog(this);
			if (result != JFileChooser.APPROVE_OPTION) {
				confirm = false;
				return;
			}
		}
		
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	/**
	* @return indicates if the dialog box has been closed with a result that is valid
	*/ 
	public boolean getConfirm() {
		return confirm;
	}
	
	/**
	* @return the template selected (should only use it if getConfirm() returns true)
	*/ 
	public NewWorldTemplate getSelectedTemplate() {
		return template;
	}
	
	/**
	* @return the width to use for the template selected (should only use it if getConfirm() returns true)
	*/ 
	public int getTemplateWidth() {
		return width;
	}

	/**
	* @return the height to use for the template selected (should only use it if getConfirm() returns true)
	*/
	public int getTemplateHeight() {
		return height;
	}

	/**
	* @return the path of the savefile to use for the template selected (should only use it if getConfirm() returns true)
	*/
	public String getSelectedSaveFilePath() {
		if (savefileChooser.getSelectedFile() != null)
			return savefileChooser.getSelectedFile().getPath();
		else
			return null;
	}
}
