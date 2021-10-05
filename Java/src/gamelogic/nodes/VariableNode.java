package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Surface;

import java.lang.Class;

/**
* The node model used to represent a single variable. <br/>
* 
* Inputs : var <br\>
* Outputs : var
* 
* @see GameManager
*/
public class VariableNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass the class object representing the type of the objects to test
	*/ 
	public VariableNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("var", dataClass));
		addOutput(new Output("var", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		T variable = getInput("var").getData(dataClass);
		getOutput("var").setData(variable);
	}

}
