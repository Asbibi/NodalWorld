package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The node model used to test equality between two objects. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public class EqualNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass the class object representing the type of the objects to test
	*/ 
	public EqualNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val1", dataClass));
		addInput(new Input("val2", dataClass));
		addOutput(new Output("res", Boolean.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		getOutput("res").setData(val1.equals(val2));
	}

}
