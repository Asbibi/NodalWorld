package gameinterface;

import gameinterface.nodaleditor.*;
import gamelogic.*;

import javax.swing.JComponent;
import javax.swing.JPanel;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Collection;

import java.lang.Class;

/**
* The controller class for nodal editors. <br/>
* 
* A nodal editor is linked to a network at construction time, and is in charge of displaying the nodes and links in this network. <br/>
* It allows to edit the network by adding or removing nodes and links, and it lets the user organize its workspace by selecting and moving or scaling nodes. 
* Adding nodes is achieved using a node menu, in which we can disable som specific nodes if we need to. <br/>
* Since the networks in this software emulate a strongly-typed language, we use a color code to let the user know the type of the inputs and outputs. <br/>
* 
* Finally, each nodal editor has a specific use : for computing either generation rules, movement rules, death rules or terrain layers. 
* Once this specification is set, the user can also link the corresponding nodes to boxes on the right side of the interface representing either species or terrain slots. <br/>
* These specific instances of nodal editors are built using the nodal editor builder.
* 
* @see NodalEditorModel
* @see NodalEditorUI
* @see NodeMenu
* @see NodalEditorBuilder
*/ 
public class NodalEditor extends JComponent {

	private NodalEditorModel model;
	private NodeMenu nodeMenu;
	private NodalEditorUI ui;

	/**
	* @param game
	* @param network
	*/ 
	public NodalEditor(GameManager game, Network network) {
		super();

		game.addTerrainListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				repaint();
			}
		});

		game.addSpeciesListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				repaint();
			}
		});

		network.addAlertListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				setOnAlert(true);
			}
		});

		model = new NodalEditorModel(game, network);
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				repaint();
			}
		});

		nodeMenu = new NodeMenu();
		nodeMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addNode(nodeMenu.getNewNode(), nodeMenu.getXInvoke(), nodeMenu.getYInvoke());
			}
		});

		ui = new NodalEditorUI();
		ui.installUI(this);
	}


	// ========== PAINTING ==========

	/**
	* @param g the graphic context
	*/ 
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ui.paint(g2d, this);
	}


	// ========== NODE MENU ==========

	public void showNodeMenu(int x, int y) { nodeMenu.show(this, x, y); }

	/**
	* Disable a specific node in the menu, which prevents the user from adding it to the network.
	* This method is used in the nodal editor builder to avoid some inconsistent behaviours from the network.
	* 
	* @param nodeName the name of the node to disable
	* 
	* @see NodalEditorBuilder
	*/ 
	public void disable(String nodeName) { nodeMenu.disable(nodeName); }


	// ========== CALLS FORWARDED TO THE MODEL ==========

	// Game Manager

	public GameManager getGameManager() { return model.getGameManager(); }

	// Network

	public Network getNetwork() { return model.getNetwork(); }

	public void link(Port portOut, Port portIn) { model.link(portOut, portIn); }

	public void unlink(Port port) { model.unlink(port); }

	public void removeNode(NodeBox box) { model.removeNode(box); }

	// Node Boxes

	public Collection<NodeBox> getBoxes() { return model.getBoxes(); }

	public NodeBox getBox(Node node) { return model.getBox(node); }

	public NodeBox getBox(int x, int y) { return model.getBox(x, y); }

	public Port getPort(Input input) { return model.getPort(input); }

	public Port getPort(Output output) { return model.getPort(output); }

	public Port getPort(int x, int y) { return model.getPort(x, y); }

	public double getScale() { return model.getScale(); }

	public void scale(double ds) { model.scale(ds); }

	public void commitScale() { model.commitScale(); }

	// Rules and Terrains

	public void setRuleCreator(Class<? extends Rule> ruleClass) { model.setRuleCreator(ruleClass); }

	public boolean isUsingRules() { return model.isUsingRules(); }

	public Class<? extends Rule> getRuleClass() { return model.getRuleClass(); }

	public void disconnectSpecies(int row) { model.disconnectSpecies(row); }

	public void setTerrainCreator() { model.setTerrainCreator(); }

	public boolean isUsingTerrains() { return model.isUsingTerrains(); }

	public void disconnectTerrainSlot(int row) { model.disconnectTerrainSlot(row); }

	public int getSideBoxWidth() { return model.getSideBoxWidth(); }

	public int getSideBoxHeight() { return model.getSideBoxHeight(); }
	
	public int getSideBoxYOffset() { return model.getSideBoxYOffset(); }
	
	public void changeSideBoxYOffset(int steps) { model.changeSideBoxYOffset(steps); repaint(); }

	public int getSpeciesRow(int x, int y) {
		if(isUsingRules() && x >= getWidth()-getSideBoxWidth() && y < getSideBoxHeight()*getGameManager().getSpeciesArray().size()) {
			return y/getSideBoxHeight();
		}
		return -1;
	}

	public int getTerrainSlotRow(int x, int y) {
		if(isUsingTerrains() && x >= getWidth()-getSideBoxWidth() && y < getSideBoxHeight()*getGameManager().getTerrain().getSlots().size()) {
			return y/getSideBoxHeight();
		}
		return -1;
	}

	// Interaction

	public void setEditingLink(boolean b) { model.setEditingLink(b); }

	public boolean isEditingLink() { return model.isEditingLink(); }

	public void setMovingSelection(boolean b) { model.setMovingSelection(b); }

	public boolean isMovingSelection() { return model.isMovingSelection(); }

	public void setSelectingArea(boolean b) { model.setSelectingArea(b); }

	public boolean isSelectingArea() { return model.isSelectingArea(); }

	public void setPanning(boolean b) { model.setPanning(b); }

	public boolean isPanning() { return model.isPanning(); }

	public void setZooming(boolean b) { model.setZooming(b); }

	public boolean isZooming() { return model.isZooming(); }

	public void setLinkingSpecies(boolean b) { model.setLinkingSpecies(b); }

	public boolean isLinkingSpecies() { return model.isLinkingSpecies(); }

	public void setLinkingTerrainSlot(boolean b) { model.setLinkingTerrainSlot(b); }

	public boolean isLinkingTerrainSlot() { return model.isLinkingTerrainSlot(); }

	public void setOnAlert(boolean b) { model.setOnAlert(b); }

	public boolean isOnAlert() { return model.isOnAlert(); }

	public void setCursorPos(int x, int y) { model.setCursorPos(x, y); }

	public int getXCursor() { return model.getXCursor(); }

	public int getYCursor() { return model.getYCursor(); }

	public void setReferencePos(int x, int y) { model.setReferencePos(x, y); }

	public int getXReference() { return model.getXReference(); }

	public int getYReference() { return model.getYReference(); }

	public void setCurrentPort(Port port) { model.setCurrentPort(port); }

	public Port getCurrentPort() { return model.getCurrentPort(); }

	public boolean isSelected(NodeBox box) { return model.isSelected(box); }

	public void clearSelection() { model.clearSelection(); }

	public void addToSelection(NodeBox box) { model.addToSelection(box); }

	public void soloSelection(NodeBox box) { model.soloSelection(box); }

	public JPanel getCurrentInfoPanel() { return model.getCurrentInfoPanel(); }

	public void setCurrentSpeciesRow(int row) { model.setCurrentSpeciesRow(row); }

	public int getCurrentSpeciesRow() { return model.getCurrentSpeciesRow(); }

	public Species getCurrentSpecies() { return model.getCurrentSpecies(); }

	public void setCurrentTerrainSlotRow(int row) { model.setCurrentTerrainSlotRow(row); }

	public int getCurrentTerrainSlotRow() { return model.getCurrentTerrainSlotRow(); }

	public TerrainSlot getCurrentTerrainSlot() { return model.getCurrentTerrainSlot(); }

	// Change Listeners

	public void addChangeListener(ChangeListener listener) { model.addChangeListener(listener); }

	public void removeChangeListener(ChangeListener listener) { model.removeChangeListener(listener); }

}
