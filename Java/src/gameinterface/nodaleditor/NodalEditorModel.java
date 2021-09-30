package gameinterface.nodaleditor;

import gamelogic.GameManager;
import gamelogic.Network;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.FontMetrics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

/**
* @see NodalEditor
*/ 
public class NodalEditorModel {

	private GameManager game;
	private Network network;
	private Collection<NodeBox> boxes;

	private boolean editingLink;
	private int xCursor, yCursor;
	private Port curPort;

	private Collection<ChangeListener> changeListeners;

	/**
	*
	*/ 
	public NodalEditorModel(GameManager game, Network network) {
		this.game = game;
		this.network = network;
		boxes = new LinkedList<NodeBox>();

		editingLink = false;

		changeListeners = new LinkedList<ChangeListener>();
	}


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
		boxes.add(new NodeBox(node, x, y));
		triggerChangeListeners();
	}

	public boolean link(Port portOut, Port portIn) {
		Node source = portOut.getBox().getNode();
		Output output = portOut.getOutput();
		Node target = portIn.getBox().getNode();
		Input input = portIn.getInput();
		return network.link(source, output.toString(), target, input.toString());
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



	// ========== Interaction ==========

	public void setEditingLink(boolean b) {
		if(editingLink != b) {
			editingLink = b;
			triggerChangeListeners();
		}
	}

	public boolean isEditingLink() { return editingLink; }

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

	public Port getCurrentPort() { return curPort; }



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
