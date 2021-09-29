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
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
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
					editor.showNodeMenu(e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO
			}
		});
	}


	// ========== Painting ==========

	/**
	* @param g2d
	* @param editor
	*/ 
	public void paint(Graphics2D g2d, NodalEditor editor) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

		Network network = editor.getNetwork();
		for(Node node : network.getNodes()) {
			NodeView view = editor.getNodeView(node);
			if(!view.isValid()) {
				view.initView(node, g2d.getFontMetrics(), 8, 4);
			}
			paintNode(g2d, editor, node, view);
		}
	}

	private void paintNode(Graphics2D g2d, NodalEditor editor, Node node, NodeView view) {
		FontMetrics metrics = g2d.getFontMetrics();
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();
		int radius = view.getPortRadius();
		int padding = 2*radius;

		g2d.setColor(Color.lightGray);
		g2d.fill(new RoundRectangle2D.Double(view.getX()-padding, view.getY(), view.getWidth()+2*padding, view.getHeight(), 2*padding, 2*padding));

		g2d.setColor(Color.black);
		int titleWidth = metrics.stringWidth(node.toString());
		g2d.drawString(node.toString(), view.getX()+(view.getWidth()-titleWidth)/2, view.getY()+lineHeight);

		for(Input input : node.getInputs()) {
			g2d.setColor(Color.black);
			g2d.drawString(input.toString(), view.getX(), view.getY()+view.getInputOffset(input));
			g2d.setColor(Color.red);
			g2d.fill(new Ellipse2D.Double(view.getX()-(padding+radius), view.getY()+view.getInputOffset(input)-2*radius, 2*radius, 2*radius));
		}

		for(Output output : node.getOutputs()) {
			g2d.setColor(Color.black);
			int wordWidth = metrics.stringWidth(output.toString());
			g2d.drawString(output.toString(), view.getX()+view.getWidth()-wordWidth, view.getY()+view.getOutputOffset(output));
			g2d.setColor(Color.red);
			g2d.fill(new Ellipse2D.Double(view.getX()+view.getWidth()+(padding-radius), view.getY()+view.getOutputOffset(output)-2*radius, 2*radius, 2*radius));
		}
	}

}
