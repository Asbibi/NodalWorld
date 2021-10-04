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
* @see Network
* @see Node
* @see GameManager
*/ 
public class NodalEditor extends JComponent {

	private NodalEditorModel model;
	private NodeMenu nodeMenu;
	private NodalEditorUI ui;

	/**
	*
	*/ 
	public NodalEditor(GameManager game, Network network) {
		super();

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

	// Species and Rules

	public void setSpeciesRuleCreator(Class<? extends Rule> ruleClass) { model.setSpeciesRuleCreator(ruleClass); }

	public boolean isUsingSpecies() { return model.isUsingSpecies(); }

	public Class<? extends Rule> getRuleClass() { return model.getRuleClass(); }

	public int getSpeciesBoxWidth() { return model.getSpeciesBoxWidth(); }

	public int getSpeciesBoxHeight() { return model.getSpeciesBoxHeight(); }

	public int getSpeciesRow(int x, int y) {
		if(isUsingSpecies() && x >= getWidth()-getSpeciesBoxWidth() && y < getSpeciesBoxHeight()*getGameManager().getSpeciesArray().size()) {
			return y/getSpeciesBoxHeight();
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

	// Change Listeners

	public void addChangeListener(ChangeListener listener) { model.addChangeListener(listener); }

	public void removeChangeListener(ChangeListener listener) { model.removeChangeListener(listener); }


	// ========== Node Menu ==========

	public void showNodeMenu(int x, int y) { nodeMenu.show(this, x, y); }

	public void disable(String nodeName) { nodeMenu.disable(nodeName); }

}
