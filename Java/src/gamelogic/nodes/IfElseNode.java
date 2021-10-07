package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The node model used to perform IF/ELSE statement. <br/>
* 
* Inputs : cond, val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public class IfElseNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass the class object representing the type of the input objects
	*/ 
	public IfElseNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("cond", Boolean.class));
		addInput(new Input("val1", dataClass));
		addInput(new Input("val2", dataClass));
		addOutput(new Output("res", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Boolean cond = getInput("cond").getData(Boolean.class);
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		if(cond) getOutput("res").setData(val1);
		else getOutput("res").setData(val2);
	}

}
