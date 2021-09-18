package gamelogic;

import java.util.Map;
import java.util.HashMap;

public abstract class Node {

	protected Map<String, Input> inputs;
	protected Map<String, Output> outputs;

	public Node() {
		inputs = new HashMap<String, Input>();
		outputs = new HashMap<String, Output>();
	}

	public void addInput(Input input) {
		inputs.put(input.getName(), input);
	}

	public Input getInput(String inputName) {
		return inputs.get(inputName);
	}

	public void addOutput(Output output) {
		outputs.put(output.getName(), output);
	}

	public Output getOutput(String outputName) {
		return outputs.get(outputName);
	}

	public abstract void evaluate(GameManager game);

}
