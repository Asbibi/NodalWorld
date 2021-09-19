package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

import java.lang.Class;

/**
* The node model used to substract two objects. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public abstract class SubNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being substracted
	*/ 
	public SubNode(Class<T> dataClass) {
		super();
		this.dataClass = dataClass;
		addInput(new Input("val1", dataClass));
		addInput(new Input("val2", dataClass));
		addOutput(new Output("res", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		getOutput("res").setData(sub(val1, val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the difference between val1 and val2
	*/ 
	abstract T sub(T val1, T val2); 

}
