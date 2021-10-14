package gameinterface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

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
	
	
	private void addPrinter() {
		printers.add(this);
	}
	
	
	
	public boolean getOnlyThisFrame() {
		return onlyThisFrame;
	}
	public boolean getPrintWithFrame() {
		return printWithFrame;
	}

	
	public void flipOnlyThisFrame() {
		onlyThisFrame = !onlyThisFrame;
		repaint();
	}

	public void flipPrintWithFrame() {
		printWithFrame = !printWithFrame;
		repaint();
	}


	
	
	static public void addMessage(PrinterMessage message) {
		messages.add(message);
		removeOldMessages();
		for (PrinterComponent printer : printers) {
			int lineNumber = (message.getFrame() - messages.get(0).getFrame()) + messages.size() + 4;
			int height = Math.max(40, lineNumber * 15);
			printer.setPreferredSize(new Dimension(300, height));

			JScrollPane scrollParent = (JScrollPane)(printer.getParent().getParent());
			if (scrollParent != null)				
				scrollParent.getVerticalScrollBar().setValue(printer.getOnlyThisFrame() ? 0 : height -20);

			printer.revalidate();
			printer.repaint();
		}
	}
	static private void removeOldMessages() {
		if (messages.size() == 0)
			return;
		
		int firstAcceptedFrame = messages.get(messages.size() - 1).getFrame() - maxFrameToKeep;
		while (messages.get(0).getFrame() < firstAcceptedFrame)
			messages.remove(0);
	}
	static public final List<PrinterMessage> getAllMessages() {
		return messages;
	}
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
	static public int getMaxFrameToKeep() {
		return maxFrameToKeep;
	}
	static public void setMaxFrameToKeep(int maxFrameToKeep) {
		PrinterComponent.maxFrameToKeep = maxFrameToKeep;
	}
	
	
}
