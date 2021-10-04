package gameinterface.nodaleditor;

import gamelogic.*;

import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.FontMetrics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import java.lang.Class;

/**
* @see NodalEditor
*/ 
public class NodalEditorModel {

	private GameManager game;
	private Network network;
	private Collection<NodeBox> boxes;
	private Map<Node, NodeInfoPanel> infoPanels;
	private boolean usingRules, usingTerrains;
	private Class<? extends Rule> ruleClass;
	private int sideBoxWidth, sideBoxHeight;

	private boolean editingLink, movingSelection, selectingArea, linkingSpecies, linkingTerrainSlot;
	private int xCursor, yCursor, xRef, yRef;
	private Port curPort;
	private Map<NodeBox, Boolean> selected;
	private JPanel curInfoPanel, defaultInfoPanel;
	private int curSpeciesRow, curTerrainSlotRow;

	private Collection<ChangeListener> changeListeners;

	/**
	*
	*/ 
	public NodalEditorModel(GameManager game, Network network) {
		this.game = game;
		this.network = network;
		boxes = new LinkedList<NodeBox>();
		infoPanels = new HashMap<Node, NodeInfoPanel>();
		usingRules = false;
		usingTerrains = false;
		ruleClass = null;
		sideBoxWidth = 100;
		sideBoxHeight = 50;

		editingLink = false;
		movingSelection = false;
		selectingArea = false;
		linkingSpecies = false;
		linkingTerrainSlot = false;
		selected = new HashMap<NodeBox, Boolean>();
		curInfoPanel = null;
		defaultInfoPanel = new JPanel();
		defaultInfoPanel.add(new JLabel("Info"));
		curSpeciesRow = -1;
		curTerrainSlotRow = -1;

		changeListeners = new LinkedList<ChangeListener>();
	}


	// ========== Game ==========

	public GameManager getGameManager() { return game; }


	// ========== Network ==========

	/**
	* @return the network the model points to
	*/ 
	public Network getNetwork() { return network; }

	/**
	* @param node
	* @param x
	* @param y
	*/ 
	public void addNode(Node node, int x, int y) {
		network.addNode(node);

		NodeBox box = new NodeBox(node, x, y);
		boxes.add(box);

		NodeInfoPanel infoPanel = new NodeInfoPanel(node);
		infoPanels.put(node, infoPanel);

		clearSelection();
		selected.put(box, true);
		curInfoPanel = infoPanel;

		triggerChangeListeners();
	}

	public void link(Port portOut, Port portIn) {
		Node source = portOut.getBox().getNode();
		Output output = portOut.getOutput();
		Node target = portIn.getBox().getNode();
		Input input = portIn.getInput();
		boolean res = network.link(source, output.toString(), target, input.toString());
		if(res) {
			triggerChangeListeners();
		}
	}

	public void unlink(Port port) {
		if(port.hasOutput() && port.getOutput().hasTarget()) {
			Node source = port.getBox().getNode();
			Output output = port.getOutput();
			Input input = output.getTarget();
			Node target = getPort(input).getBox().getNode();
			network.unlink(source, output.toString(), target, input.toString());
		} else if(port.hasInput() && port.getInput().hasSource()) {
			Node target = port.getBox().getNode();
			Input input = port.getInput();
			Output output = input.getSource();
			Node source = getPort(output).getBox().getNode();
			network.unlink(source, output.toString(), target, input.toString());
		}
	}


	// ========== Node Boxes and Ports ==========

	/**
	* @return all the node boxes
	*/ 
	public Collection<NodeBox> getBoxes() { return boxes; }

	public NodeBox getBox(Node node) {
		Optional<NodeBox> opt = boxes.stream()
									.filter(box -> box.getNode().equals(node))
									.findFirst();
		return opt.orElse(null);
	}

	public NodeBox getBox(int x, int y) {
		Optional<NodeBox> opt = boxes.stream()
									.filter(box -> box.hit(x, y))
									.findFirst();
		return opt.orElse(null);
	}

	public Port getPort(Input input) {
		Optional<Port> opt = boxes.stream()
								.flatMap(box -> box.getPorts().stream())
								.filter(port -> input.equals(port.getInput()))
								.findFirst();
		return opt.orElse(null);
	}

	public Port getPort(Output output) {
		Optional<Port> opt = boxes.stream()
								.flatMap(box -> box.getPorts().stream())
								.filter(port -> output.equals(port.getOutput()))
								.findFirst();
		return opt.orElse(null);
	}

	public Port getPort(int x, int y) {
		Optional<Port> opt = boxes.stream()
								.flatMap(box -> box.getPorts().stream())
								.filter(port -> port.hit(x, y))
								.findFirst();
		return opt.orElse(null);
	}


	// ========== Rules and Terrains ==========

	public void setRuleCreator(Class<? extends Rule> ruleClass) {
		usingRules = true;
		this.ruleClass = ruleClass;
	}

	public boolean isUsingRules() { return usingRules; }

	public Class<? extends Rule> getRuleClass() { return ruleClass; }

	public void setTerrainCreator() {
		usingTerrains = true;
	}

	public boolean isUsingTerrains() { return usingTerrains; }

	public int getSideBoxWidth() { return sideBoxWidth; }

	public int getSideBoxHeight() { return sideBoxHeight; }


	// ========== Interaction ==========

	public void setEditingLink(boolean b) {
		if(editingLink != b) {
			editingLink = b;
			triggerChangeListeners();
		}
	}

	public boolean isEditingLink() { return editingLink; }

	public void setMovingSelection(boolean b) {
		if(movingSelection != b) {
			movingSelection = b;
			triggerChangeListeners();
		}
	}

	public boolean isMovingSelection() { return movingSelection; }

	public void setSelectingArea(boolean b) {
		if(selectingArea != b) {
			selectingArea = b;
			triggerChangeListeners();
		}
	}

	public boolean isSelectingArea() { return selectingArea; }

	public void setLinkingSpecies(boolean b) {
		if(linkingSpecies != b) {
			linkingSpecies = b;
			triggerChangeListeners();
		}
	}

	public boolean isLinkingSpecies() { return linkingSpecies; }

	public void setLinkingTerrainSlot(boolean b) {
		if(linkingTerrainSlot != b) {
			linkingTerrainSlot = b;
			triggerChangeListeners();
		}
	}

	public boolean isLinkingTerrainSlot() { return linkingTerrainSlot; }

	public void setCursorPos(int x, int y) {
		xCursor = x;
		yCursor = y;
		triggerChangeListeners();
	}

	public int getXCursor() { return xCursor; }

	public int getYCursor() { return yCursor; }

	public void setCurrentPort(Port port) {
		curPort = port;
		triggerChangeListeners();
	}

	public void setReferencePos(int x, int y) {
		xRef = x;
		yRef = y;
		triggerChangeListeners();
	}

	public int getXReference() { return xRef; }

	public int getYReference() { return yRef; }

	public Port getCurrentPort() { return curPort; }

	public boolean isSelected(NodeBox box) { return selected.get(box); }

	public void clearSelection() {
		selected.replaceAll((k, v) -> false);
		triggerChangeListeners();
	}

	public void addToSelection(NodeBox box) {
		if(!selected.get(box)) {
			selected.replace(box, true);
			triggerChangeListeners();
		}
	}

	public void soloSelection(NodeBox box) {
		selected.replaceAll((k, v) -> false);
		selected.replace(box, true);
		curInfoPanel = infoPanels.get(box.getNode());
		triggerChangeListeners();
	}

	public JPanel getCurrentInfoPanel() {
		return ((curInfoPanel==null) ? defaultInfoPanel : curInfoPanel);
	}

	public void setCurrentSpeciesRow(int row) {
		curSpeciesRow = row;
		triggerChangeListeners();
	}

	public int getCurrentSpeciesRow() { return curSpeciesRow; }

	public Species getCurrentSpecies() { return game.getSpecies(curSpeciesRow); }

	public void setCurrentTerrainSlotRow(int row) {
		curTerrainSlotRow = row;
		triggerChangeListeners();
	}

	public int getCurrentTerrainSlotRow() { return curTerrainSlotRow; }

	public TerrainSlot getCurrentTerrainSlot() { return game.getTerrain().getSlot(curTerrainSlotRow); }


	// ========== Change Listeners ==========

	/**
	* @param listener
	*/ 
	public void addChangeListener(ChangeListener listener) { changeListeners.add(listener); }

	/**
	* @param listener
	*/ 
	public void removeChangeListener(ChangeListener listener) { changeListeners.remove(listener); }

	private void triggerChangeListeners() {
		for(ChangeListener listener : changeListeners)
			listener.stateChanged(new ChangeEvent(this));
	}

}
