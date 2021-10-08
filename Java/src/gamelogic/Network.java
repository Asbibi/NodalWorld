package gamelogic;

import java.util.List;
import java.util.Collection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import javax.swing.event.ChangeListener;

import gameinterface.nodaleditor.NodalEditorModel;

import javax.swing.event.ChangeEvent;

/**
* This class provides the abstract model for the networks with in the nodal system when defining rules for the game.
* A network can be evaluated, meaning it will evaluate all its nodes in a topological order (to avoid inconsistencies).
* Nodes can be added and linked, but an edge will be refused if it creates a cycle, thus maintaining a directed-acyclic graph structure at all times.
* 
* @see Node
* @see Edge
*/ 
public class Network implements Serializable {

	private List<Node> nodes;
	private Map<Node, Collection<Edge>> outEdges, inEdges;
	
	private NodalEditorModel attachedModel = null;

	private transient List<ChangeListener> alertListeners;

	/**
	* Creates an empty network.
	*/ 
	public Network() {
		nodes = new ArrayList<Node>();
		outEdges = new HashMap<Node, Collection<Edge>>();
		inEdges = new HashMap<Node, Collection<Edge>>();

		alertListeners = new LinkedList<ChangeListener>();
	}
	public void initTransientFields() {
		if (alertListeners == null)
			alertListeners = new LinkedList<ChangeListener>();
	}
	public void copyFromLoadedAndAttachEditorModel(NodalEditorModel newModel) {
		if (attachedModel != null)
			newModel.copyNonTransientFields(attachedModel);
		attachedModel = newModel;
	}

	/**
	* @return all the network's nodes
	*/ 
	public List<Node> getNodes() { return nodes; }

	/**
	* @param node
	*/ 
	public void addNode(Node node) {
		nodes.add(node);
		outEdges.put(node, new ArrayList<Edge>());
		inEdges.put(node, new ArrayList<Edge>());
	}

	/**
	* @param source
	* @param outputName
	* @param target
	* @param inputName
	* @result true if the nodes were successfully linked - meaning the output and input type matched and no cycle were created -, otherwise false
	*/ 
	public boolean link(Node source, String outputName, Node target, String inputName) {
		Output output = source.getOutput(outputName);
		Input input = target.getInput(inputName);
		if(!input.getDataClass().equals(output.getDataClass())) {
			return false;
		}

		Edge edge = new Edge(source, target);
		outEdges.get(source).add(edge);
		boolean isDAG = sortNodes();
		if(!isDAG) {
			outEdges.get(source).remove(edge);
			return false;
		}
		inEdges.get(target).add(edge);

		input.setSource(output);
		output.setTarget(input);
		return true;
	}

	/**
	* @param source
	* @param outputName
	* @param target
	* @param inputName
	*/ 
	public void unlink(Node source, String outputName, Node target, String inputName) {
		Output output = source.getOutput(outputName);
		Input input = target.getInput(inputName);
		
		output.removeTarget();
		input.removeSource();

		Optional<Edge> optEdge = outEdges.get(source).stream()
									.filter(edge -> edge.getTarget().equals(target))
									.findFirst();
		if(optEdge.isPresent()) {
			outEdges.get(source).remove(optEdge.get());
			inEdges.get(target).remove(optEdge.get());
		}
	}

	/**
	* @param game
	*/ 
	public void evaluate(GameManager game, Node sink) throws NetworkIOException {
		try {
			Set<Node> dependencies = findDependencies(sink);

			for(Node node : nodes) {
				if(dependencies.contains(node))
					node.evaluate(game);
			}

		} catch(NetworkIOException e) {
			triggerAlertListeners();
			throw e;
		}
	}
	
	public void replaceSurfaceByEmpty(Surface replacedSurface) {
		for (Node node : nodes) {
			node.replaceSurfaceInputByEmpty(replacedSurface);
		}
	}
	public void replaceSpeciesByEmpty(Species replacedSpecies) {
		for (Node node : nodes) {
			node.replaceSpeciesInputByEmpty(replacedSpecies);
		}
	}

	/**
	* @param listener
	*/ 
	public void addAlertListener(ChangeListener listener) { alertListeners.add(listener); }

	/**
	*
	*/ 
	public void triggerAlertListeners() {
		for(ChangeListener listener : alertListeners) 
			listener.stateChanged(new ChangeEvent(this));
	}


	// ========== PRIVATE ==========

	private boolean sortNodes() {
		Map<Node, Integer> inDegree = new HashMap<Node, Integer>();
		for(Node node : nodes) {
			inDegree.put(node, 0);
		}
		for(Node source : nodes) {
			for(Edge edge : outEdges.get(source)) {
				Node target = edge.getTarget();
				inDegree.replace(target, inDegree.get(target)+1);
			}
		}

		Map<Node, Integer> priority = new HashMap<Node, Integer>();
		int counter = 0;
		int nNodesLeft = nodes.size();
		Queue<Node> queue = new LinkedList<Node>();
		for(Node node : nodes) {
			if(inDegree.get(node)==0) {
				queue.add(node);
				priority.put(node, counter);
				counter++;
			}
		}

		while(!queue.isEmpty()) {
			Node source = queue.poll();
			for(Edge edge : outEdges.get(source)) {
				Node target = edge.getTarget();
				inDegree.replace(target, inDegree.get(target)-1);
				if(inDegree.get(target)==0) {
					queue.add(target);
					priority.put(target, counter);
					counter++;
				}
			}
			nNodesLeft--;
		}

		if(nNodesLeft > 0) return false;

		class CompareNodesByPriority implements Comparator<Node> {
			@Override
			public int compare(Node n1, Node n2) {
				return Integer.compare(priority.get(n1), priority.get(n2));
			}
		}
		Collections.sort(nodes, new CompareNodesByPriority());
		return true;
	}

	private Set<Node> findDependencies(Node sink) {
		Set<Node> dependencies = new HashSet<Node>();

		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(sink);
		dependencies.add(sink);

		while(!queue.isEmpty()) {
			Node head = queue.pollFirst();

			for(Edge edge : inEdges.get(head)) {
				Node ancestor = edge.getSource();
				if(dependencies.add(ancestor)) queue.add(ancestor);
			}
		}

		return dependencies;
	}

}
