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

public class Network {

	private List<Node> nodes;
	private Map<Node, Collection<Edge>> mapToEdges;

	public Network() {
		nodes = new ArrayList<Node>();
		mapToEdges = new HashMap<Node, Collection<Edge>>();
	}

	public void addNode(Node node) {
		nodes.add(node);
		mapToEdges.put(node, new ArrayList<Edge>());
	}

	public boolean link(Node source, String outputName, Node target, String inputName) {
		Output output = source.getOutput(outputName);
		Input input = target.getInput(inputName);
		if(!input.isCompatibleWith(output)) {
			return false;
		}

		Edge edge = new Edge(source, target);
		mapToEdges.get(source).add(edge);
		boolean isValid = sortNodes();
		if(!isValid) {
			mapToEdges.get(source).remove(edge);
			return false;
		}

		input.setSource(output);
		return true;
	}

	public void evaluate(GameManager game) {
		for(Node node : nodes) {
			node.evaluate(game);
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

}
