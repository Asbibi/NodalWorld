package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Output;

import java.lang.Class;

/**
* The node model used to store a single value. <br/>
* 
* Inputs : none <br/>
* Outputs : val
* 
* @see GameManager
*/
public class ValueNode<T> extends Node {

	private Class<T> dataClass;
	private T value;

	/**
	* @param dataClass the class object representing the type of the objects to test
	*/ 
	public ValueNode(Class<T> dataClass) {
		super();
		this.dataClass = dataClass;
		addOutput(new Output("val", dataClass));
	}

	/**
	* @param v
	*/ 
	public void setValue(T v) {
		this.value = v;
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		getOutput("val").setData(value);
	}

}
