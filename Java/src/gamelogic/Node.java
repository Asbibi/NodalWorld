package gamelogic;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Collection;

/**
* This class provides the abstract node model for the nodes used in the nodal system.
* A node can be evaluated, meaning it will retrieve data from its input(s), do calculations with it, and store the result(s) in its output(s).
* To create a new node, extend this class in the package gamelogic.nodes, define its inputs and outputs in the constructor and override the evaluate method.
* 
* @see Input
* @see Output
*/ 
public abstract class Node implements Serializable {

	private static final long serialVersionUID = 6501070277350305776L;

	private static int idCounter = 0;

	private int id;
	protected String name;
	protected Map<String, Input> inputs;
	protected Map<String, Output> outputs;

	/**
	* @param name
	*/ 
	public Node(String name) {
		id = idCounter;
		idCounter++;
		this.name = name;
		inputs = new HashMap<String, Input>();
		outputs = new HashMap<String, Output>();
	}

	/**
	* Check if two nodes are equal using their ids.
	*/ 
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
	* Compute data using (potential) inputs and store result in (potential) outputs.
	* 
	* @param game
	*/ 
	public abstract void evaluate(GameManager game) throws NetworkIOException;
	
	/**
	* Utility method used to avoid inconsistent behaviour when deleting a surface.
	* 
	* @param replacedSurface
	*/ 
	public void replaceSurfaceInputByEmpty(Surface replacedSurface) {
		for (Input input : inputs.values()) {
			try {
				if (input.isManual() && (input.<Surface>getManualValue(Surface.class)).equals(replacedSurface))
					input.setManualValue(Surface.getEmpty());
			} catch (NetworkIOException e) {
				// this catch is normal : it happens when adressing an input that isn't a Surface -> ignore it
				return;
			}
		}
	}

	/**
	* Utility method used to avoid inconsistent behaviour when deleting a species.
	* 
	* @param replacedSpecies
	*/ 
	public void replaceSpeciesInputByEmpty(Species replacedSpecies) {
		for (Input input : inputs.values()) {
			try {
				if (input.isManual() && (input.<Species>getManualValue(Species.class)).equals(replacedSpecies))
					input.setManualValue(Species.getEmpty());
			} catch (NetworkIOException e) {
				// this catch is normal : it happens when adressing an input that isn't a Species -> ignore it				
				return;
			}
		}
	}


	// ========== LOADING ==========

	/**
	* This method synchronizes the idCounter on this node, 
	* making it bigger than this node's id
	*/ 
	public void synchIdCounter() {
		idCounter = Math.max(idCounter, id+1);
	}
}
