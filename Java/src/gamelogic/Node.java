package gamelogic;

import java.util.Map;
import java.util.HashMap;

/**
* This class provides the abstract node model for the nodes used in the nodal system.
* A node can be evaluated, meaning it will retrieve data from its input(s), do calculations with it, and store the result(s) in its output(s).
* To create a new node, extend this class in the package gamelogic.nodes.
* 
* @see Input
* @see Output
*/ 
public abstract class Node {

	protected Map<String, Input> inputs;
	protected Map<String, Output> outputs;

	/**
	*
	*/ 
	public Node() {
		inputs = new HashMap<String, Input>();
		outputs = new HashMap<String, Output>();
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
	* @return true if all inputs are connected to a source, otherwise false
	*/ 
	public boolean allInputsConnected() {
		return inputs.values().stream().allMatch(input -> input.hasSource());
	}

	/**
	* @param game
	*/ 
	public abstract void evaluate(GameManager game);

}
