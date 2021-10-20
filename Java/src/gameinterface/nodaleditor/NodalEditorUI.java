package gameinterface.nodaleditor;

import gameinterface.NodalEditor;
import gamelogic.*;

import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.FontMetrics;

import java.util.LinkedList;
import java.util.Optional;

/**
* The presentation (view + interaction) of the nodal editor. 
* 
* @see NodalEditor
*/ 
public class NodalEditorUI {


	// ========== Interaction ==========

	/**
	* Initializes the editor's interaction behaviour 
	* 
	* @param editor
	*/ 
	public void installUI(NodalEditor editor) {
		editor.setFocusable(true);

		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				editor.setOnAlert(false);
				editor.requestFocus();

				// Zooming
				if(e.isShiftDown()) {
					editor.setReferencePos(e.getX(), e.getY());
					editor.setCursorPos(e.getX(), e.getY());
					editor.setZooming(true);
					return;
				}

				// Panning
				if(e.isControlDown()) {
					editor.setReferencePos(e.getX(), e.getY());
					editor.setCursorPos(e.getX(), e.getY());
					editor.setPanning(true);
					return;
				}

				// Selecting a port (input or output) to add or remove a link
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
						editor.setCurrentPort(portHit);
					}
					editor.setCursorPos(e.getX(), e.getY());
					editor.setEditingLink(true);
					return;
				}

				// Selecting a node box 
				// the user can start moving the selection
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

				// Selecting a species (when using them) to add or remove a link to a terminal node
				int speciesRow = editor.getSpeciesRow(e.getX(), e.getY() + editor.getSideBoxYOffset());
				if(speciesRow >= 0) {
					editor.setCurrentSpeciesRow(speciesRow);
					editor.disconnectSpecies(speciesRow);
					editor.setCursorPos(e.getX(), e.getY());
					editor.setLinkingSpecies(true);
					return;
				}

				// Selecting a terrain slot (when using them) to add or remove a link to a terrain node
				int terrainSlotRow = editor.getTerrainSlotRow(e.getX(), e.getY() + editor.getSideBoxYOffset());
				if(terrainSlotRow >= 0) {
					editor.setCurrentTerrainSlotRow(terrainSlotRow);
					editor.disconnectTerrainSlot(terrainSlotRow);
					editor.setCursorPos(e.getX(), e.getY());
					editor.setLinkingTerrainSlot(true);
					return;
				}

				// Default press on background clears the selection
				editor.clearSelection();
				editor.setReferencePos(e.getX(), e.getY());
				editor.setCursorPos(e.getX(), e.getY());
				editor.setSelectingArea(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Node menu appears on right click
				if(SwingUtilities.isRightMouseButton(e)) {
					editor.showNodeMenu(e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(editor.isEditingLink()) {
					// If the user tried to create a new link
					Port portHit = editor.getPort(e.getX(), e.getY());
					if(portHit != null) {

						// Delete previous one if it exists
						editor.unlink(portHit);

						// Create new link
						if(editor.getCurrentPort().hasInput() && portHit.hasOutput()) {
							editor.link(portHit, editor.getCurrentPort());
						} else if(editor.getCurrentPort().hasOutput() && portHit.hasInput()) {
							editor.link(editor.getCurrentPort(), portHit);
						}
					}
					editor.setEditingLink(false);

				} else if(editor.isMovingSelection()) {
					// Record the translation as permanent
					for(NodeBox box : editor.getBoxes()) {
						if(editor.isSelected(box)) box.commitTranslate();
					}
					editor.setMovingSelection(false);

				} else if(editor.isPanning()) {
					// Record the translation as permanent
					for(NodeBox box : editor.getBoxes()) box.commitTranslate();
					editor.setPanning(false);

				} else if(editor.isZooming()) {
					// Record the scaling as permanent
					editor.commitScale();
					editor.setZooming(false);

				} else if(editor.isSelectingArea()) {
					// Compute rectangular selection area
					int x = Math.min(editor.getXCursor(), editor.getXReference());
					int y = Math.min(editor.getYCursor(), editor.getYReference());
					int w = Math.abs(editor.getXCursor()-editor.getXReference());
					int h = Math.abs(editor.getYCursor()-editor.getYReference());
					Rectangle2D selectRect = new Rectangle2D.Double(x, y, w, h);

					// Add all nodes that intersects it to the selection
					for(NodeBox box : editor.getBoxes()) {
						Rectangle2D boxRect = new Rectangle2D.Double(box.getX()-box.getPadding(), box.getY(), box.getWidth()+2*box.getPadding(), box.getHeight());
						if(selectRect.intersects(boxRect)) {
							editor.addToSelection(box);
						}
					}
					editor.setSelectingArea(false);

				} else if(editor.isLinkingSpecies()) {
					// Connect a rule to a species via a terminal node
					NodeBox box = editor.getBox(e.getX(), e.getY());
					if(box != null && box.getNode() instanceof TerminalNode) {
						editor.getGameManager().connectRuleToSpecies(((TerminalNode) box.getNode()).getRule(), editor.getCurrentSpecies());
					}
					editor.setLinkingSpecies(false);

				} else if(editor.isLinkingTerrainSlot()) {
					// Connect a terrain model to a slot via a terrain node
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
				// Record current mouse position
				editor.setCursorPos(e.getX(), e.getY());

				if(editor.isMovingSelection()) {
					// Temporary translation
					int tx = editor.getXCursor()-editor.getXReference();
					int ty = editor.getYCursor()-editor.getYReference();
					for(NodeBox box : editor.getBoxes()) {
						if(editor.isSelected(box)) box.translate(tx, ty);
					}

				} else if(editor.isPanning()) {
					// Temporary translation
					int tx = editor.getXCursor()-editor.getXReference();
					int ty = editor.getYCursor()-editor.getYReference();
					for(NodeBox box : editor.getBoxes()) {
						box.translate(tx, ty);
					}

				} else if(editor.isZooming()) {
					// Temporary scaling
					double ds = Math.tanh((editor.getXCursor()-editor.getXReference())*0.001);
					editor.scale(ds);
				}
			}
		});
		
		editor.addMouseWheelListener(new MouseWheelListener() {
		    public void mouseWheelMoved(MouseWheelEvent e) {
			    editor.changeSideBoxYOffset(e.getWheelRotation());
		    }
	    });

		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)  {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					// Delete nodes in selection
					LinkedList<NodeBox> boxesToRemove = new LinkedList<NodeBox>();
					for(NodeBox box : editor.getBoxes()) {
						if(editor.isSelected(box)) boxesToRemove.add(box);
					}
					for(NodeBox box : boxesToRemove) editor.removeNode(box);
				}
			}
		});
	}


	// ========== Painting ==========

	private static Color slotDisconnectedColor = new Color(192,192,222);
	private static Color slotConnectedColor = new Color(192,222,192);
	/**
	* @param g2d
	* @param editor
	*/ 
	public void paint(Graphics2D g2d, NodalEditor editor) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		g2d.setStroke(new BasicStroke(3));

		// Initialize node box geometry if it hasn't already been done
		for(NodeBox box : editor.getBoxes()) {
			if(!box.isValid()) {
				box.init(6, 5, 4, g2d.getFontMetrics());
			}
		}

		// Links between terminal nodes and species
		if(editor.isUsingRules()) {
			GameManager game = editor.getGameManager();
			int row = -1;
			for(Species sp : game.getSpeciesArray()) {
				if(row >= 0) {
					Rectangle2D rect = new Rectangle2D.Double(editor.getWidth()-editor.getSideBoxWidth(), row*editor.getSideBoxHeight() - editor.getSideBoxYOffset(), editor.getSideBoxWidth(), editor.getSideBoxHeight());
					boolean isConnected = game.getRule(editor.getRuleClass(), sp) != null;
					g2d.setColor(isConnected ? slotConnectedColor : slotDisconnectedColor);
					g2d.fill(rect);
					g2d.setColor(Color.gray);
					g2d.draw(rect);
					g2d.setColor(Color.black);
					g2d.drawString(sp.toString(), editor.getWidth()-editor.getSideBoxWidth()+10, row*editor.getSideBoxHeight() - editor.getSideBoxYOffset() + 30);

					if(isConnected) {
						NodeBox box = editor.getBox(game.getRule(editor.getRuleClass(), sp).getTerminalNode());
						g2d.setColor(new Color(120,255,0));
						g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*(row+0.5) - editor.getSideBoxYOffset(), box.getX()+box.getWidth(), box.getY()));
					}
				}

				row++;
			}

			// Draw incomplete link
			if(editor.isLinkingSpecies()) {
				g2d.setColor(Color.red);
				g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*(editor.getCurrentSpeciesRow()+0.5) - editor.getSideBoxYOffset(), editor.getXCursor(), editor.getYCursor()));
			}
		}

		// Links between terrain nodes and slots
		if(editor.isUsingTerrains()) {
			Terrain terrain = editor.getGameManager().getTerrain();
			int row = 0;
			for(TerrainSlot slot : terrain.getSlots()) {
				Rectangle2D rect = new Rectangle2D.Double(editor.getWidth()-editor.getSideBoxWidth(), row*editor.getSideBoxHeight()  - editor.getSideBoxYOffset(), editor.getSideBoxWidth(), editor.getSideBoxHeight());
				boolean isConnected = slot.isOccupied();
				g2d.setColor(isConnected ? slotConnectedColor : slotDisconnectedColor);
				g2d.fill(rect);
				g2d.setColor(Color.gray);
				g2d.draw(rect);
				g2d.setColor(Color.black);
				g2d.drawString("slot "+row, editor.getWidth()-editor.getSideBoxWidth()+10, row*editor.getSideBoxHeight() - editor.getSideBoxYOffset() +30);

				if(isConnected) {
					NodeBox box = editor.getBox(slot.getTerrainNode());
					g2d.setColor(Color.green);
					g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*(row+0.5)  - editor.getSideBoxYOffset(), box.getX()+box.getWidth(), box.getY()));
				}

				row++;
			}

			// Draw incomplete link
			if(editor.isLinkingTerrainSlot()) {
				g2d.setColor(Color.red);
				g2d.draw(new Line2D.Double(editor.getWidth()-editor.getSideBoxWidth(), editor.getSideBoxHeight()*(editor.getCurrentTerrainSlotRow()+0.5) - editor.getSideBoxYOffset(), editor.getXCursor(), editor.getYCursor()));
			}
		}

		// Draw nodes (adapt font size to scale)
		double baseFontSize = g2d.getFont().getSize();
		g2d.setFont(g2d.getFont().deriveFont((float) (baseFontSize*editor.getScale())));
		for(NodeBox box : editor.getBoxes()) {
			box.paint(g2d, editor);
		}

		// Draw links
		for(NodeBox box : editor.getBoxes()) {
			for(Port p : box.getPorts()) {
				if(p.hasInput() && p.getInput().hasSource()) {
					Port q = editor.getPort(p.getInput().getSource());
					g2d.setColor(q.getColor());
					g2d.draw(new Line2D.Double(p.getX(), p.getY(), q.getX(), q.getY()));
				}
			}
		}
		
		// Draw incomplete link
		if(editor.isEditingLink()) {
			g2d.setColor(Color.red);
			g2d.draw(new Line2D.Double(editor.getCurrentPort().getX(), editor.getCurrentPort().getY(), editor.getXCursor(), editor.getYCursor()));
		}

		// Selection area
		if(editor.isSelectingArea()) {
			int x = Math.min(editor.getXCursor(), editor.getXReference());
			int y = Math.min(editor.getYCursor(), editor.getYReference());
			int w = Math.abs(editor.getXCursor()-editor.getXReference());
			int h = Math.abs(editor.getYCursor()-editor.getYReference());
			Rectangle2D selectRect = new Rectangle2D.Double(x, y, w, h);
			g2d.setColor(new Color(255, 255, 0, 50));
			g2d.fill(selectRect);
		}

		// Alert signal when an execution of network failed
		if(editor.isOnAlert()) {
			g2d.setColor(new Color(255, 0, 0, 100));
			Rectangle2D alertRect = new Rectangle2D.Double(0, 0, editor.getSize().width, editor.getSize().height);
			g2d.fill(alertRect);
		}
	}

}
