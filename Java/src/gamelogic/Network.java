package gamelogic;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

/**
* This class provides the abstract model for the networks with in the nodal system when defining rules for the game.
* A network can be evaluated, meaning it will evaluate all its nodes in a topological order (to avoid inconsistencies).
* Nodes can be added and linked, but an edge will be refused if it creates a cycle, thus maintaining a directed-acyclic graph structure at all times.
* 
* @see Node
* @see Edge
*/ 
public class Network {

	private List<Node> nodes;
	private Map<Node, Collection<Edge>> mapToEdges;

	/**
	* Creates an empty network.
	*/ 
	public Network() {
		nodes = new ArrayList<Node>();
		mapToEdges = new HashMap<Node, Collection<Edge>>();
	}

	/**
	* @param node
	*/ 
	public void addNode(Node node) {
		nodes.add(node);
		mapToEdges.put(node, new ArrayList<Edge>());
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
		mapToEdges.get(source).add(edge);
		boolean isDAG = sortNodes();
		if(!isDAG) {
			mapToEdges.get(source).remove(edge);
			return false;
		}

		input.setSource(output);
		return true;
	}

	/**
	* @param game
	*/ 
	public void evaluate(GameManager game) {
		if(checkConnections()) {
			for(Node node : nodes) {
				node.evaluate(game);
			}
		}
	}

	private boolean sortNodes() {
		Map<Node, Integer> inDegree = new HashMap<Node, Integer>();
		for(Node node : nodes) {
			inDegree.put(node, 0);
		}
		for(Node source : nodes) {
			for(Edge edge : mapToEdges.get(source)) {
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
			for(Edge edge : mapToEdges.get(source)) {
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

	private boolean checkConnections() {
		return nodes.stream().allMatch(node -> node.allInputsConnected());
	}

}
