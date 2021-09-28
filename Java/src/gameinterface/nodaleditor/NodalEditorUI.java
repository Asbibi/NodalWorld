package gameinterface.nodaleditor;

import gameinterface.NodalEditor;
import gamelogic.Network;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.nodes.*;

import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;

/**
* @see NodalEditor
*/ 
public class NodalEditorUI {

	// ========== Interaction ==========

	/**
	* @param editor
	*/ 
	public void installUI(NodalEditor editor) {
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					// TODO : display a menu that allows the user to select a node to add
					editor.addNode(new GenerateNode(), e.getX(), e.getY());
				}
			}
		});
	}


	// ========== Painting ==========

	/**
	* @param g2d
	* @param editor
	*/ 
	public void paint(Graphics2D g2d, NodalEditor editor) {
		Network network = editor.getNetwork();
		for(Node node : network.getNodes()) {
			paintNode(g2d, editor, node);
		}
	}

	private void paintNode(Graphics2D g2d, NodalEditor editor, Node node) {
		FontMetrics metrics = g2d.getFontMetrics();
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();

		// compute width and height of bounding box
		int width = metrics.stringWidth("    ");
		int height = 0;
		int maxWidth, dy;

		maxWidth = 0;
		dy = lineHeight; // reserve one line for node's name
		for(Input input : node.getInputs()) {
			maxWidth = Math.max(maxWidth, metrics.stringWidth(input.toString()));
			dy += lineHeight;
		}
		dy += lineHeight; // reserve space for the last line
		width += maxWidth;
		height = Math.max(height, dy);

		maxWidth = 0;
		dy = lineHeight; // reserve one line for node's name
		for(Output output : node.getOutputs()) {
			maxWidth = Math.max(maxWidth, metrics.stringWidth(output.toString()));
			dy += lineHeight;
		}
		dy += lineHeight; // reserve space for the last line
		width += maxWidth;
		height = Math.max(height, dy);

		int titleWidth = metrics.stringWidth(node.toString());
		width = Math.max(width, titleWidth);

		// draw bounding box
		g2d.setColor(Color.cyan);
		NodeView view = editor.getNodeView(node);
		g2d.fill(new Rectangle2D.Double(view.getX(), view.getY(), width, height));

		g2d.setColor(Color.black);
		// draw node's name
		g2d.drawString(node.toString(), view.getX()+(width-titleWidth)/2, view.getY()+lineHeight);

		// draw inputs' name
		dy = lineHeight;
		for(Input input : node.getInputs()) {
			dy += lineHeight;
			g2d.drawString(input.toString(), view.getX(), view.getY()+dy);
		}

		// draw outputs' name
		dy = lineHeight;
		for(Output output : node.getOutputs()) {
			dy += lineHeight;
			int wordWidth = metrics.stringWidth(output.toString());
			g2d.drawString(output.toString(), view.getX()+width-wordWidth, view.getY()+dy);
		}
	}

}
