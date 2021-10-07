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
* The controller class for nodal editors. 
* 
* @see Network
* @see Node
* @see NodeEditorBuilder
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


	/**
	* @param g the graphic context
	*/ 
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ui.paint(g2d, this);
	}


	// ========== Calls forwarded to the model ==========

	// Game Manager

	public GameManager getGameManager() { return model.getGameManager(); }

	// Network

	public Network getNetwork() { return model.getNetwork(); }

	public void link(Port portOut, Port portIn) { model.link(portOut, portIn); }

	public void unlink(Port port) { model.unlink(port); }

	// Node Boxes

	public Collection<NodeBox> getBoxes() { return model.getBoxes(); }

	public NodeBox getBox(Node node) { return model.getBox(node); }

	public NodeBox getBox(int x, int y) { return model.getBox(x, y); }

	public Port getPort(Input input) { return model.getPort(input); }

	public Port getPort(Output output) { return model.getPort(output); }

	public Port getPort(int x, int y) { return model.getPort(x, y); }

	// Rules and Terrains

	public void setRuleCreator(Class<? extends Rule> ruleClass) { model.setRuleCreator(ruleClass); }

	public boolean isUsingRules() { return model.isUsingRules(); }

	public Class<? extends Rule> getRuleClass() { return model.getRuleClass(); }

	public void setTerrainCreator() { model.setTerrainCreator(); }

	public boolean isUsingTerrains() { return model.isUsingTerrains(); }

	public int getSideBoxWidth() { return model.getSideBoxWidth(); }

	public int getSideBoxHeight() { return model.getSideBoxHeight(); }

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


	// ========== Node Menu ==========

	public void showNodeMenu(int x, int y) { nodeMenu.show(this, x, y); }

	public void disable(String nodeName) { nodeMenu.disable(nodeName); }

}
