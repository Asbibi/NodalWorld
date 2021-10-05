package gamelogic;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
* This class provides the abstract node model for the nodes used in the nodal system.
* A node can be evaluated, meaning it will retrieve data from its input(s), do calculations with it, and store the result(s) in its output(s).
* To create a new node, extend this class in the package gamelogic.nodes.
* 
* @see Input
* @see Output
*/ 
public abstract class Node {

	private static int idCounter = 0;

	private int id;
	protected String name;
	protected Map<String, Input> inputs;
	protected Map<String, Output> outputs;

	/**
	*
	*/ 
	public Node(String name) {
		id = idCounter;
		idCounter++;
		this.name = name;
		inputs = new HashMap<String, Input>();
		outputs = new HashMap<String, Output>();
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Node)) return false;

		Node node = (Node) o;
		return (id == node.id);
	}

	/**
	* @return the node's name
	*/ 
	@Override
	public String toString() {
		return name;
	}

	/**
	* @param input
	*/ 
	public void addInput(Input input) {
		inputs.put(input.toString(), input);
	}

	/**
	* @param inputName
	* @return the corresponding input, null if it doesn't exist
	*/ 
	public Input getInput(String inputName) {
		return inputs.get(inputName);
	}

	/**
	* @return all the node's inputs
	*/ 
	public Collection<Input> getInputs() {
		return inputs.values();
	}

	/**
	* @param output
	*/ 
	public void addOutput(Output output) {
		outputs.put(output.toString(), output);
	}

	/**
	* @param outputName
	* @return the corresponding output, null if it doesn't exist
	*/ 
	public Output getOutput(String outputName) {
		return outputs.get(outputName);
	}

	/**
	* @return all the node's outputs
	*/ 
	public Collection<Output> getOutputs() {
		return outputs.values();
	}

	/**
	* @return true if all inputs are connected to a source, otherwise false
	*/ 
	public boolean allInputsValid() {
		return inputs.values().stream().allMatch(input -> (input.hasSource() || input.isManual()));
	}

	/**
	* Compute data using potential inputs and store result in potential outputs.
	* 
	* @param game
	*/ 
	public abstract void evaluate(GameManager game);

}
