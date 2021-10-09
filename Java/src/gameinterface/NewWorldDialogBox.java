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

import gameinterface.components.NewWorldTemplateComponent;
import gamelogic.ImageFile;

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
	
	private NewWorldTemplateComponent emptyPanel;
	private NewWorldTemplateComponent basicPanel;
	private NewWorldTemplateComponent islandPanel;
	private NewWorldTemplateComponent completeDemoPanel;
	private NewWorldTemplateComponent loadElementsPanel;
	private NewWorldTemplateComponent loadElementsTerrainPanel;
	private NewWorldTemplateComponent loadElementsAllNodesPanel;
	private NewWorldTemplateComponent loadFullSavePanel;
	
	
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
		templatePanel.add(emptyPanel = new NewWorldTemplateComponent(NewWorldTemplate.empty));
		templatePanel.add(basicPanel = new NewWorldTemplateComponent(NewWorldTemplate.basic));
		templatePanel.add(islandPanel = new NewWorldTemplateComponent(NewWorldTemplate.island));
		templatePanel.add(completeDemoPanel = new NewWorldTemplateComponent(NewWorldTemplate.completeDemo));
		templatePanel.add(loadElementsPanel = new NewWorldTemplateComponent(NewWorldTemplate.loadElements));
		templatePanel.add(loadElementsTerrainPanel = new NewWorldTemplateComponent(NewWorldTemplate.loadElementsTerrain));
		templatePanel.add(loadElementsAllNodesPanel = new NewWorldTemplateComponent(NewWorldTemplate.loadElementsAllNodes));
		templatePanel.add(loadFullSavePanel = new NewWorldTemplateComponent(NewWorldTemplate.loadFullSave));
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
		widthInputField.setFieldString("10");
		heightInputField.setFieldString("10");
		
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

	private void selectTemplatePanel(NewWorldTemplateComponent selectedTemplatePanel) {
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
	
	private void onOkButtonPressed() {
		confirm = true;
		if (template.getAskDimension()) {
			try {
				width = Integer.parseInt(widthInputField.getFieldString());
				widthInputField.setFieldColor(ControlPanel.getStandardFieldColor());
			} catch (Exception except) {
				widthInputField.setFieldColor(ControlPanel.getWrongFieldColor());
				confirm = false;
			}
			try {
				height = Integer.parseInt(heightInputField.getFieldString());
				heightInputField.setFieldColor(ControlPanel.getStandardFieldColor());
			} catch (Exception except) {
				heightInputField.setFieldColor(ControlPanel.getWrongFieldColor());
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
	

	public boolean getConfirm() {
		return confirm;
	}
	public NewWorldTemplate getSelectedTemplate() {
		return template;
	}

	public int getTemplateWidth() {
		return width;
	}

	public int getTemplateHeight() {
		return height;
	}

	public String getSelectedSaveFilePath() {
		if (savefileChooser.getSelectedFile() != null)
			return savefileChooser.getSelectedFile().getPath();
		else
			return null;
	}
}
