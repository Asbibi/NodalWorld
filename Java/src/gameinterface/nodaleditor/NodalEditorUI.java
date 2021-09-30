package gameinterface.nodaleditor;

import gameinterface.NodalEditor;
import gamelogic.Network;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.nodes.*;

import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.FontMetrics;

import java.util.Optional;

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
			public void mousePressed(MouseEvent e) {
				Port portHit = editor.getPort(e.getX(), e.getY());
				if(portHit != null) {
					if(portHit.hasInput()) {
						Input input = portHit.getInput();
						if(input.hasSource()) {
							Output output = input.getSource();
							Port portOut = editor.getPort(output);
							editor.unlink(portHit);
							editor.setCurrentPort(portOut);
						} else {
							editor.setCurrentPort(portHit);
						}
					} else {
						Output output = portHit.getOutput();
						if(output.hasTarget()) {
							Input input = output.getTarget();
							Port portIn = editor.getPort(input);
							editor.unlink(portHit);
							editor.setCurrentPort(portIn);
						} else {
							editor.setCurrentPort(portHit);
						}
					}
					editor.setCursorPos(e.getX(), e.getY());
					editor.setEditingLink(true);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					// TODO
				} else if(SwingUtilities.isRightMouseButton(e)) {
					editor.showNodeMenu(e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(editor.isEditingLink()) {
					Port portHit = editor.getPort(e.getX(), e.getY());

					if(portHit != null) {
						editor.unlink(portHit);

						if(editor.getCurrentPort().hasInput() && portHit.hasOutput()) {
							editor.link(portHit, editor.getCurrentPort());
						} else if(editor.getCurrentPort().hasOutput() && portHit.hasInput()) {
							editor.link(editor.getCurrentPort(), portHit);
						}
					}
					editor.setEditingLink(false);
				}
			}
		});

		editor.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(editor.isEditingLink()) {
					editor.setCursorPos(e.getX(), e.getY());
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
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

		for(NodeBox box : editor.getBoxes()) {
			if(!box.isValid()) {
				box.init(6, 5, 4, g2d.getFontMetrics());
			}
			paintNode(g2d, editor, box);
		}

		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.magenta);
		for(NodeBox box : editor.getBoxes()) {
			for(Port p : box.getPorts()) {
				if(p.hasOutput() && p.getOutput().hasTarget()) {
					Port q = editor.getPort(p.getOutput().getTarget());
					g2d.draw(new Line2D.Double(p.getX(), p.getY(), q.getX(), q.getY()));
				}
			}
		}
		

		if(editor.isEditingLink()) {
			g2d.setColor(Color.red);
			g2d.draw(new Line2D.Double(editor.getCurrentPort().getX(), editor.getCurrentPort().getY(), editor.getXCursor(), editor.getYCursor()));
		}
	}

	private void paintNode(Graphics2D g2d, NodalEditor editor, NodeBox box) {
		FontMetrics metrics = g2d.getFontMetrics();
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();

		g2d.setColor(Color.lightGray);
		g2d.fill(new RoundRectangle2D.Double(box.getX()-box.getPadding(), box.getY(), box.getWidth()+2*box.getPadding(), box.getHeight(), 2*box.getPadding(), 2*box.getPadding()));

		g2d.setColor(Color.black);
		int titleWidth = metrics.stringWidth(box.getNode().toString());
		g2d.drawString(box.getNode().toString(), box.getX()+(box.getWidth()-titleWidth)/2, box.getY()+lineHeight);

		for(Port port : box.getPorts()) {
			g2d.setColor(Color.red);
			g2d.fill(new Ellipse2D.Double(port.getX()-port.getSize(), port.getY()-port.getSize(), 2*port.getSize(), 2*port.getSize()));
			g2d.setColor(Color.black);
			if(port.hasInput()) {
				g2d.drawString(port.getInput().toString(), box.getX(), port.getY());
			} else {
				int wordWidth = metrics.stringWidth(port.getOutput().toString());
				g2d.drawString(port.getOutput().toString(), box.getX()+box.getWidth()-wordWidth, port.getY());
			}
		}
	}

}
