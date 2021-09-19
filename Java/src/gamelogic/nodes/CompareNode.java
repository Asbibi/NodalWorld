package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

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
	* @param dataClass the class object representing the type of the objects to compare
	*/ 
	public CompareNode(Class<T> dataClass) {
		super();
		this.dataClass = dataClass;
		addInput(new Input("val1", dataClass));
		addInput(new Input("val2", dataClass));
		addOutput(new Output("res", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		int comp = val1.compareTo(val2);
		getOutput("res").setData(comp < 0);
	}

}
