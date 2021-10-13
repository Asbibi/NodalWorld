package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;
import java.lang.Comparable;

/**
* The node model used to compare two objects. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public class CompareNode<T extends Comparable<T>> extends Node {

	private Class<T> dataClass;

	/**
	* @param name
	* @param dataClass the class object representing the type of the objects to compare
	*/ 
	public CompareNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("a", dataClass));
		addInput(new Input("b", dataClass));
		addOutput(new Output("a<b", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("a").getData(dataClass);
		T val2 = getInput("b").getData(dataClass);
		int comp = val1.compareTo(val2);
		getOutput("a<b").setData(comp < 0);
	}

}
