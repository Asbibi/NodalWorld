package gameinterface.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import gameinterface.PrinterMessage;

/**
* The view of a ImageCompoent.
* 
* @see PrinterComponent
*/ 
public class PrinterView {

	/**
	* @param graphics2D the Graphic Context to use
	* @param component the PrinterComponent to display
	*/
	public void paint(Graphics2D g, PrinterComponent owner) {
		g.setColor(Color.white);
		g.fillRect(0, 0, owner.getWidth(), owner.getHeight());
		
		boolean whithFrameNumber = owner.getPrintWithFrame();
		int lineHeight = 15;
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.setColor(Color.darkGray);
		
		if(owner.getOnlyThisFrame()) {
			List<PrinterMessage> messages = PrinterComponent.getLastFrameMessages();
			if(messages == null || messages.size() == 0)
				return;
			
			paintFrameSeparation(g, messages.get(0).getFrame(), lineHeight);
			int lastMessageIndex = messages.size() - 1;
			for (int i = lastMessageIndex; i > -1; i--)
				paintMessage(g, messages.get(i), ((lastMessageIndex-i) + 2)*lineHeight, whithFrameNumber);
		}
		else {
			List<PrinterMessage> messages = PrinterComponent.getAllMessages();
			if(messages.size() == 0)
				return;
			
			int currentFrame = messages.get(0).getFrame();
			int yTextPos = lineHeight;
			paintFrameSeparation(g, currentFrame, yTextPos);
			for (int i = 0; i < messages.size(); i++) {
				if (currentFrame != messages.get(i).getFrame()) {
					currentFrame = messages.get(i).getFrame();
					yTextPos += lineHeight;
					paintFrameSeparation(g, currentFrame, yTextPos);
				}
				yTextPos += lineHeight;
				paintMessage(g, messages.get(i), yTextPos, whithFrameNumber);
			}
		}
	}
	
	/**
	* Prints a message on the given line. 
	* @param graphics2D the Graphic Context to use
	* @param message the message to print on this line
	* @param yTextPos the y position of this line
	* @param withFrame indicates if the frame must be printed on the message
	*/
	private void paintMessage(Graphics2D g, PrinterMessage message, int yTextPos, boolean withFrame) {
		g.drawString(message.getMessage(withFrame), 2, yTextPos);
	}
	
	/**
	* Prints a special line to indicate a change of frame. 
	* @param graphics2D the Graphic Context to use
	* @param frame the frame to print in the speparator
	* @param yTextPos the y position of the separator line
	*/
	private void paintFrameSeparation(Graphics2D g, int frame, int yTextPos) {
		g.drawString("=========   Frame: " + frame + "   =========", 2, yTextPos);		
	}
}
