package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;

import java.lang.Class;

/**
* The node model used to perform IF/ELSE statement.
* 
* @see GameManager
*/
public class IfElseNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the input objects
	*/ 
	public IfElseNode(Class<T> dataClass) {
		super();
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
	public void evaluate(GameManager game) {
		Boolean cond = getInput("cond").getData(Boolean.class);
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		if(cond) getOutput("res").setData(val1);
		else getOutput("res").setData(val2);
	}

}
