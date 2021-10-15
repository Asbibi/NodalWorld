package gameinterface.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import gameinterface.PrinterMessage;

public class PrinterView {

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
	
	private void paintMessage(Graphics2D g, PrinterMessage message, int yTextPos, boolean withFrame) {
		g.drawString(message.getMessage(withFrame), 2, yTextPos);
	}
	private void paintFrameSeparation(Graphics2D g, int frame, int yTextPos) {
		g.drawString("=========   Frame: " + frame + "   =========", 2, yTextPos);		
	}
}
