package gameinterface.nodaleditor;

import gameinterface.NodalEditor;
import gamelogic.*;

import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.Rectangle2D;
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
					return;
				}

				NodeBox box = editor.getBox(e.getX(), e.getY());
				if(box != null) {
					if(!editor.isSelected(box)) {
						editor.soloSelection(box);
					}
					editor.setReferencePos(e.getX(), e.getY());
					editor.setCursorPos(e.getX(), e.getY());
					editor.setMovingSelection(true);
					return;
				}

				int speciesRow = editor.getSpeciesRow(e.getX(), e.getY());
				if(speciesRow >= 0) {
					editor.setCurrentSpeciesRow(speciesRow);
					editor.setCursorPos(e.getX(), e.getY());
					editor.setLinkingSpecies(true);
					return;
				}

				int terrainSlotRow = editor.getTerrainSlotRow(e.getX(), e.getY());
				if(terrainSlotRow >= 0) {
					editor.setCurrentTerrainSlotRow(terrainSlotRow);
					editor.setCursorPos(e.getX(), e.getY());
					editor.setLinkingTerrainSlot(true);
					return;
				}

				editor.clearSelection();
				editor.setReferencePos(e.getX(), e.getY());
				editor.setCursorPos(e.getX(), e.getY());
				editor.setSelectingArea(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
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

				} else if(editor.isMovingSelection()) {
					int dx = editor.getXCursor()-editor.getXReference();
					int dy = editor.getYCursor()-editor.getYReference();
					for(NodeBox box : editor.getBoxes()) {
						if(editor.isSelected(box)) box.translate(dx, dy);
					}
					editor.setMovingSelection(false);

				} else if(editor.isSelectingArea()) {
					int x = Math.min(editor.getXCursor(), editor.getXReference());
					int y = Math.min(editor.getYCursor(), editor.getYReference());
					int w = Math.abs(editor.getXCursor()-editor.getXReference());
					int h = Math.abs(editor.getYCursor()-editor.getYReference());
					Rectangle2D selectRect = new Rectangle2D.Double(x, y, w, h);
					for(NodeBox box : editor.getBoxes()) {
						Rectangle2D boxRect = new Rectangle2D.Double(box.getX()-box.getPadding(), box.getY(), box.getWidth()+2*box.getPadding(), box.getHeight());
						if(selectRect.intersects(boxRect)) {
							editor.addToSelection(box);
						}
					}
					editor.setSelectingArea(false);

				} else if(editor.isLinkingSpecies()) {
					NodeBox box = editor.getBox(e.getX(), e.getY());
					if(box != null && box.getNode() instanceof TerminalNode) {
						editor.getGameManager().connectRuleToSpecies(((TerminalNode) box.getNode()).getRule(), editor.getCurrentSpecies());
					}
					editor.setLinkingSpecies(false);

				} else if(editor.isLinkingTerrainSlot()) {
					NodeBox box = editor.getBox(e.getX(), e.getY());
					if(box != null && box.getNode() instanceof TerrainNode) {
						editor.getCurrentTerrainSlot().connect((TerrainNode) (box.getNode()));
					}
					editor.setLinkingTerrainSlot(false);

				}
			}
		});

		editor.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				editor.setCursorPos(e.getX(), e.getY());
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
		g2d.setStroke(new BasicStroke(3));

		for(NodeBox box : editor.getBoxes()) {
			if(!box.isValid()) {
				box.init(6, 5, 4, g2d.getFontMetrics());
			}
			paintNode(g2d, editor, box);
		}

		if(editor.isUsingRules()) {
			GameManager game = editor.getGameManager();
			int row = 0;
			for(Species sp : game.getSpeciesArray()) {
				Rectangle2D rect = new Rectangle2D.Double(editor.getWidth()-editor.getSideBoxWidth(), row*editor.getSideBoxHeight(), editor.getSideBoxWidth(), editor.getSideBoxHeight());
				g2d.setColor(Color.lightGray);
				g2d.fill(rect);
				g2d.setColor(Color.gray);
				g2d.draw(rect);
				g2d.setColor(Color.black);
				g2d.drawString(sp.toString(), editor.getWidth()-editor.getSideBoxWidth()+10, editor.getSideBoxHeight()*row+10);

				if(game.getRule(editor.getRuleClass(), sp) != null) {
					NodeBox box = editor.getBox(game.getRule(editor.getRuleClass(), sp).getTerminalNode());
					boolean translate = editor.isMovingSelection() && editor.isSelected(box);
					int dx = (translate) ? editor.getXCursor()-editor.getXReference() : 0;
					int dy = (translate) ? editor.getYCursor()-editor.getYReference() : 0;
					g2d.setColor(Color.green);
					g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*row+10, box.getX()+dx, box.getY()+dy));
				}

				row++;
			}

			if(editor.isLinkingSpecies()) {
				g2d.setColor(Color.red);
				g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*editor.getCurrentSpeciesRow()+10, editor.getXCursor(), editor.getYCursor()));
			}
		}

		if(editor.isUsingTerrains()) {
			Terrain terrain = editor.getGameManager().getTerrain();
			int row = 0;
			for(TerrainSlot slot : terrain.getSlots()) {
				Rectangle2D rect = new Rectangle2D.Double(editor.getWidth()-editor.getSideBoxWidth(), row*editor.getSideBoxHeight(), editor.getSideBoxWidth(), editor.getSideBoxHeight());
				g2d.setColor(Color.lightGray);
				g2d.fill(rect);
				g2d.setColor(Color.gray);
				g2d.draw(rect);
				g2d.setColor(Color.black);
				g2d.drawString("slot "+row, editor.getWidth()-editor.getSideBoxWidth()+10, editor.getSideBoxHeight()*row+10);

				if(slot.isOccupied()) {
					NodeBox box = editor.getBox(slot.getTerrainNode());
					boolean translate = editor.isMovingSelection() && editor.isSelected(box);
					int dx = (translate) ? editor.getXCursor()-editor.getXReference() : 0;
					int dy = (translate) ? editor.getYCursor()-editor.getYReference() : 0;
					g2d.setColor(Color.green);
					g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*row+10, box.getX()+dx, box.getY()+dy));
				}

				row++;
			}

			if(editor.isLinkingTerrainSlot()) {
				g2d.setColor(Color.red);
				g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*editor.getCurrentTerrainSlotRow()+10, editor.getXCursor(), editor.getYCursor()));
			}
		}

		g2d.setColor(Color.magenta);
		for(NodeBox box : editor.getBoxes()) {
			for(Port p : box.getPorts()) {
				if(p.hasOutput() && p.getOutput().hasTarget()) {
					Port q = editor.getPort(p.getOutput().getTarget());

					boolean translateP = editor.isMovingSelection() && editor.isSelected(box);
					int dxP = (translateP) ? editor.getXCursor()-editor.getXReference() : 0;
					int dyP = (translateP) ? editor.getYCursor()-editor.getYReference() : 0;

					boolean translateQ = editor.isMovingSelection() && editor.isSelected(q.getBox());
					int dxQ = (translateQ) ? editor.getXCursor()-editor.getXReference() : 0;
					int dyQ = (translateQ) ? editor.getYCursor()-editor.getYReference() : 0;

					g2d.draw(new Line2D.Double(p.getX()+dxP, p.getY()+dyP, q.getX()+dxQ, q.getY()+dyQ));
				}
			}
		}
		

		if(editor.isEditingLink()) {
			g2d.setColor(Color.red);
			g2d.draw(new Line2D.Double(editor.getCurrentPort().getX(), editor.getCurrentPort().getY(), editor.getXCursor(), editor.getYCursor()));
		}

		if(editor.isSelectingArea()) {
			int x = Math.min(editor.getXCursor(), editor.getXReference());
			int y = Math.min(editor.getYCursor(), editor.getYReference());
			int w = Math.abs(editor.getXCursor()-editor.getXReference());
			int h = Math.abs(editor.getYCursor()-editor.getYReference());
			Rectangle2D selectRect = new Rectangle2D.Double(x, y, w, h);
			g2d.setColor(new Color(255, 255, 0, 50));
			g2d.fill(selectRect);
		}
	}

	private void paintNode(Graphics2D g2d, NodalEditor editor, NodeBox box) {
		FontMetrics metrics = g2d.getFontMetrics();
		int lineHeight = metrics.getAscent()+metrics.getDescent()+metrics.getLeading();

		boolean translate = editor.isMovingSelection() && editor.isSelected(box);
		int dx = (translate) ? editor.getXCursor()-editor.getXReference() : 0;
		int dy = (translate) ? editor.getYCursor()-editor.getYReference() : 0;

		RoundRectangle2D rect = new RoundRectangle2D.Double(box.getX()-box.getPadding()+dx, box.getY()+dy, box.getWidth()+2*box.getPadding(), box.getHeight(), 2*box.getPadding(), 2*box.getPadding());
		if(editor.isSelected(box)) {
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.pink);
			g2d.draw(rect);
		}
		g2d.setColor(Color.lightGray);
		g2d.fill(rect);

		g2d.setColor(Color.black);
		int titleWidth = metrics.stringWidth(box.getNode().toString());
		g2d.drawString(box.getNode().toString(), box.getX()+(box.getWidth()-titleWidth)/2+dx, box.getY()+lineHeight+dy);

		for(Port port : box.getPorts()) {
			g2d.setColor(Color.red);
			g2d.fill(new Ellipse2D.Double(port.getX()-port.getSize()+dx, port.getY()-port.getSize()+dy, 2*port.getSize(), 2*port.getSize()));
			g2d.setColor(Color.black);
			if(port.hasInput()) {
				g2d.drawString(port.getInput().toString(), box.getX()+dx, port.getY()+dy);
			} else {
				int wordWidth = metrics.stringWidth(port.getOutput().toString());
				g2d.drawString(port.getOutput().toString(), box.getX()+box.getWidth()-wordWidth+dx, port.getY()+dy);
			}
		}
	}

}
