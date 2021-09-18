package gamelogic;

public class Edge {

	private Node source, target;

	public Edge(Node source, Node target) {
		this.source = source;
		this.target = target;
	}

	public Node getSource() {
		return source;
	}

	public Node getTarget() {
		return target;
	}

}
