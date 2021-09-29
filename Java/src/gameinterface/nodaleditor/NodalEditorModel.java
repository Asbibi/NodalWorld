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
import java.util.Map;
import java.util.HashMap;

/**
* @see NodalEditor
*/ 
public class NodalEditorModel {

	private GameManager game;
	private Network network;
	private Map<Node, NodeView> nodeViews;

	private Collection<ChangeListener> changeListeners;

	/**
	*
	*/ 
	public NodalEditorModel(GameManager game, Network network) {
		this.game = game;
		this.network = network;
		nodeViews = new HashMap<Node, NodeView>();

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
		nodeViews.put(node, new NodeView(x, y));
		triggerChangeListeners();
	}


	// ========== Node View ==========

	/**
	* @param node
	* @return the corresponding node view
	*/ 
	public NodeView getNodeView(Node node) { return nodeViews.get(node); }



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
