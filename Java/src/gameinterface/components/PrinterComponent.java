package gameinterface.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import gameinterface.PrinterMessage;

/**
* Component printing the messages that the print nodes send.<br/>
* This class has a static list of messages that the print nodes aliment. The messages in this list are only kept a certain number frames that is also a static attribute of the class.<br/><br/>
*
* A printer alone is composed of a dynamically resized white rectangle on which the messages are displayed.<br/>
* Each printer has the option to print every messages in memory or only the one corresponding to the current frame.<br/>
* It also has the option to print the related frame in the message.
* 
* @see PrinterMessage
* @see PrinterView
*/
public class PrinterComponent extends JComponent {

	static private List<PrinterComponent> printers = new ArrayList<>();
	static private List<PrinterMessage> messages = new ArrayList<>();
	static private int maxFrameToKeep = 10;
	
	private PrinterView view;
	private boolean onlyThisFrame = true;
	private boolean printWithFrame = false;
	
	public PrinterComponent() {
		view = new PrinterView();
		setSize(new Dimension(100,300));
		addPrinter();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		view.paint((Graphics2D)g, this);
	}
	
	
	/**
	* Add this printer to the static list of Printers.
	*/
	private void addPrinter() {
		printers.add(this);
	}
	
	
	/**
	* @return the printer's option to only display the last frame (true) or every message saved (false)
	*/
	public boolean getOnlyThisFrame() {
		return onlyThisFrame;
	}
	/**
	* @return the printer's option to display the frame in the message (true) or only the message (false)
	*/
	public boolean getPrintWithFrame() {
		return printWithFrame;
	}

	/**
	* Changes the printer's option to only display the last frame or not.
	*/
	public void flipOnlyThisFrame() {
		onlyThisFrame = !onlyThisFrame;
		repaint();
	}
	/**
	* Changes the printer's option to display the frame in the message or not.
	*/
	public void flipPrintWithFrame() {
		printWithFrame = !printWithFrame;
		repaint();
	}


	

	/**
	* Adds a message to the static message list.
	* @param message
	*/
	static public void addMessage(PrinterMessage message) {
		messages.add(message);
		removeOldMessages();
		for (PrinterComponent printer : printers) {
			int lineNumber = (message.getFrame() - messages.get(0).getFrame()) + messages.size() + 4;
			int height = Math.max(40, lineNumber * 15);
			printer.setPreferredSize(new Dimension(550, height));

			JScrollPane scrollParent = (JScrollPane)(printer.getParent().getParent());
			if (scrollParent != null)				
				scrollParent.getVerticalScrollBar().setValue(printer.getOnlyThisFrame() ? 0 : height -20);

			printer.revalidate();
			printer.repaint();
		}
	}
	/**
	* Remove the messages from frames that are too old (i.e. from an earlier frame than the last message's frame - maxFrameToKeep)
	*/
	static private void removeOldMessages() {
		if (messages.size() == 0)
			return;
		
		int firstAcceptedFrame = messages.get(messages.size() - 1).getFrame() - maxFrameToKeep;
		while (messages.get(0).getFrame() < firstAcceptedFrame)
			messages.remove(0);
	}
	/**
	* @return all the messages in memory
	*/
	static public final List<PrinterMessage> getAllMessages() {
		return messages;
	}
	/**
	* Because of how the selection is done, the list return has the messages in the reverse order of receiving.
	* @return all the messages in memory corresponding to the last frame that has emit a message
	*/
	static public final List<PrinterMessage> getLastFrameMessages() {
		if (messages.size() == 0)
			return null;
		
		int lastFrame = messages.get(messages.size() -1).getFrame();
		List<PrinterMessage> frameMessages = new ArrayList<PrinterMessage>();	
		for (int i = messages.size() -1; i > -1; i--) {
			if (messages.get(i).getFrame() != lastFrame)
				break;
			frameMessages.add(messages.get(i));
		}
		return frameMessages;
	}
	/**
	* @return maxFrameToKeep, the maximum number of frame between first and last messages in memory
	*/
	static public int getMaxFrameToKeep() {
		return maxFrameToKeep;
	}
	/**
	* @return maxFrameToKeep sets maxFrameToKeep, the maximum number of frame between first and last messages in memory
	*/
	static public void setMaxFrameToKeep(int maxFrameToKeep) {
		PrinterComponent.maxFrameToKeep = maxFrameToKeep;
	}
	
	
}
