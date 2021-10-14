package gameinterface;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class PrinterToolBar extends JToolBar{
	private PrinterComponent printer;
	
	private JToggleButton printFrameNumber;
	private JToggleButton printAllFrames;
	private TextFieldPanel frameNumberField;

	public PrinterToolBar() {
		super(null, JToolBar.VERTICAL);
		add(new JLabel("Node Printer"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		printer = new PrinterComponent();
		JScrollPane scrollPane = new JScrollPane(printer);
		scrollPane.setPreferredSize(new Dimension(100,200));
		add(scrollPane);
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(0,2,25,0));
		optionPanel.add(printFrameNumber = new JToggleButton("Frame Number"));
		optionPanel.add(printAllFrames = new JToggleButton("All Frames"));
		add(optionPanel);
		printFrameNumber.addActionListener(e -> printer.flipPrintWithFrame());
		printAllFrames.addActionListener(e -> printer.flipOnlyThisFrame());
		add(frameNumberField = new TextFieldPanel("Buffer"));
		frameNumberField.setFieldString(Integer.toString(PrinterComponent.getMaxFrameToKeep()));
		frameNumberField.addActionListener( e -> applyToTerrain() );
	}
	
	private void applyToTerrain() {		
		try {
			PrinterComponent.setMaxFrameToKeep(Integer.parseInt(frameNumberField.getFieldString()));
			frameNumberField.setFieldColor(ControlPanel.getStandardFieldColor());
		} catch (Exception e) {
			frameNumberField.setFieldColor(ControlPanel.getWrongFieldColor());		
		}	
	}
}
