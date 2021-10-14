package gameinterface.nodaleditor;

import gamelogic.*;
import gamelogic.rules.*;

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

import java.io.Serializable;

import java.lang.Class;

/**
* The model behind the nodal editor.
* It uses boxes and ports for creating and suppressing connections in the network. 
*  
* @see NodalEditor
*/ 
public class NodalEditorModel implements Serializable {

	private transient GameManager game;
	private transient Network network;
	
	private Collection<NodeBox> boxes;
	private double scale, ds;
	private Map<Node, NodeInfoPanel> infoPanels;
	private boolean usingRules, usingTerrains;
	private Class<? extends Rule> ruleClass;
	private int sideBoxWidth, sideBoxHeight;

	private transient boolean editingLink, movingSelection, selectingArea, panning, zooming, linkingSpecies, linkingTerrainSlot, onAlert;
	private transient int xCursor, yCursor, xRef, yRef;
	private transient Port curPort;

	private Map<NodeBox, Boolean> selected;
	private transient JPanel curInfoPanel, defaultInfoPanel;
	private transient int curSpeciesRow, curTerrainSlotRow;

	private transient Collection<ChangeListener> changeListeners;

	/**
	* @param game
	* @param network
	*/ 
	public NodalEditorModel(GameManager game, Network network) {
		this.game = game;
		this.network = network;
		boxes = new LinkedList<NodeBox>();
		scale = 1;
		ds = 0;
		infoPanels = new HashMap<Node, NodeInfoPanel>();
		usingRules = false;
		usingTerrains = false;
		ruleClass = null;
		sideBoxWidth = 100;
		sideBoxHeight = 50;

		editingLink = false;
		movingSelection = false;
		selectingArea = false;
		panning = false;
		zooming = false;
		linkingSpecies = false;
		linkingTerrainSlot = false;
		onAlert = false;
		selected = new HashMap<NodeBox, Boolean>();
		curInfoPanel = null;
		defaultInfoPanel = new JPanel();
		defaultInfoPanel.add(new JLabel("Info"));
		curSpeciesRow = -1;
		curTerrainSlotRow = -1;

		changeListeners = new LinkedList<ChangeListener>();
		
		// copy needed model data from loded save file
		network.copyFromLoadedAndAttachEditorModel(this);
	}
	public void copyNonTransientFields(NodalEditorModel otherModel) {
		boxes = otherModel.boxes;
		usingRules = otherModel.usingRules;
		usingTerrains = otherModel.usingTerrains;
		ruleClass = otherModel.ruleClass;
		sideBoxWidth = otherModel.sideBoxWidth;
		sideBoxHeight = otherModel.sideBoxHeight;
		selected = otherModel.selected;

		//infoPanels = otherModel.infoPanels;
		for (Node node : otherModel.infoPanels.keySet()) {
			NodeInfoPanel infoPanel = new NodeInfoPanel(game, node);
			infoPanels.put(node, infoPanel);
		}
		System.out.println("info panel loaded with: " + game);
		
		/*
		 NodeInfoPanel infoPanel = new NodeInfoPanel(game, node);
		infoPanels.put(node, infoPanel);
		 */
	}


	// ========== Game ==========

	/**
	* @return the game manager
	*/ 
	public GameManager getGameManager() { return game; }


	// ========== Network ==========

	/**
	* @return the network the model represents
	*/ 
	public Network getNetwork() { return network; }

	/**
	* @param node
	* @param x
	* @param y
	*/ 
	public void addNode(Node node, int x, int y) {
		network.addNode(node);

		NodeBox box = new NodeBox(node, x, y, scale);
		boxes.add(box);

		NodeInfoPanel infoPanel = new NodeInfoPanel(game, node);
		infoPanels.put(node, infoPanel);

		clearSelection();
		selected.put(box, true);
		curInfoPanel = infoPanel;

		triggerChangeListeners();
	}

	public void removeNode(NodeBox box) {
		network.removeNode(box.getNode());
		infoPanels.remove(box.getNode());
		boxes.remove(box);

		if(isUsingRules() && box.getNode() instanceof TerminalNode) {
			game.disconnectRule(((TerminalNode) box.getNode()).getRule());
		}

		if(isUsingTerrains() && box.getNode() instanceof TerrainNode) {
			for(TerrainSlot slot : game.getTerrain().getSlots()) {
				if(slot.isOccupied() && slot.getTerrainNode() == box.getNode()) slot.disconnect();
			}
		}

		triggerChangeListeners();
	}

	/**
	* @param portOut
	* @param portIn
	*/ 
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

	/**
	* @param port
	*/ 
	public void unlink(Port port) {
		if(port.hasInput() && port.getInput().hasSource()) {
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

	/**
	* @param node 
	* @return the box corresponding to the given node (if it exists)
	*/ 
	public NodeBox getBox(Node node) {
		Optional<NodeBox> opt = boxes.stream()
									.filter(box -> box.getNode().equals(node))
									.findFirst();
		return opt.orElse(null);
	}

	/**
	* @param x
	* @param y
	* @return a box found at the given position (if it exists)
	*/ 
	public NodeBox getBox(int x, int y) {
		Optional<NodeBox> opt = boxes.stream()
									.filter(box -> box.hit(x, y))
									.findFirst();
		return opt.orElse(null);
	}

	/**
	* @param input
	* @return the port corresponding to the given input (if it exists)
	*/ 
	public Port getPort(Input input) {
		Optional<Port> opt = boxes.stream()
								.flatMap(box -> box.getPorts().stream())
								.filter(port -> input.equals(port.getInput()))
								.findFirst();
		return opt.orElse(null);
	}

	/**
	* @param output
	* @return the port corresponding to the given output (if it exists)
	*/ 
	public Port getPort(Output output) {
		Optional<Port> opt = boxes.stream()
								.flatMap(box -> box.getPorts().stream())
								.filter(port -> output.equals(port.getOutput()))
								.findFirst();
		return opt.orElse(null);
	}

	/**
	* @param x
	* @param y
	* @return a port found at the given position (if it exists)
	*/ 
	public Port getPort(int x, int y) {
		Optional<Port> opt = boxes.stream()
								.flatMap(box -> box.getPorts().stream())
								.filter(port -> port.hit(x, y))
								.findFirst();
		return opt.orElse(null);
	}

	public double getScale() { return scale*(1+ds); }

	public void scale(double ds) {
		this.ds = ds;
		for(NodeBox box : boxes) box.scale(ds);
		triggerChangeListeners();
	}

	public void commitScale() {
		scale = getScale();
		ds = 0;
		for(NodeBox box : boxes) box.commitScale();
		triggerChangeListeners();
	}


	// ========== Rules and Terrains ==========

	/**
	* Enables the editor to handle rules and species
	*  
	* @param ruleClass the specific rule class to handle
	*/ 
	public void setRuleCreator(Class<? extends Rule> ruleClass) {
		usingRules = true;
		this.ruleClass = ruleClass;
	}

	/**
	* @return true if the editor can handle rules, otherwise false
	*/ 
	public boolean isUsingRules() { return usingRules; }

	/**
	* @return the specific rule class the editor can handle
	*/ 
	public Class<? extends Rule> getRuleClass() { return ruleClass; }

	public void disconnectSpecies(int row) {
		Species sp = game.getSpecies(row+1);
		if(ruleClass == GenerationRule.class) game.disconnectGenRuleFromSpecies(sp);
		else if(ruleClass == MovementRule.class) game.disconnectMoveRuleFromSpecies(sp);
		else if(ruleClass == DeathRule.class) game.disconnectDeathRuleFromSpecies(sp);

		triggerChangeListeners();
	}

	/**
	* Enables the editor th handle terrains
	*/ 
	public void setTerrainCreator() {
		usingTerrains = true;
	}

	/**
	* @return true if the editor can handle terrains, otherwise false
	*/ 
	public boolean isUsingTerrains() { return usingTerrains; }

	public void disconnectTerrainSlot(int row) {
		TerrainSlot slot = game.getTerrain().getSlot(row);
		if(slot.isOccupied()) slot.disconnect();

		triggerChangeListeners();
	}

	/**
	* @return width of side box for specific handling behaviours (rules or terrains)
	*/ 
	public int getSideBoxWidth() { return sideBoxWidth; }

	/**
	* @return height of side box for specific handling behaviours (rules or terrains)
	*/ 
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

	public void setPanning(boolean b) {
		if(panning != b) {
			panning = b;
			triggerChangeListeners();
		}
	}

	public boolean isPanning() { return panning; }

	public void setZooming(boolean b) {
		if(zooming != b) {
			zooming = b;
			triggerChangeListeners();
		}
	}

	public boolean isZooming() { return zooming; }

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

	public void setOnAlert(boolean b) {
		if(onAlert != b) {
			onAlert = b;
			triggerChangeListeners();
		}
	}

	public boolean isOnAlert() { return onAlert; }

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

	public Species getCurrentSpecies() { return game.getSpecies(curSpeciesRow+1); }

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
