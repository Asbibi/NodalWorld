package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The node model used to perform an IF/ELSE statement. <br/>
* 
* Inputs : cond, true val, false val <br/>
* Outputs : res
* 
*/
public class IfElseNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public IfElseNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("cond", Boolean.class));
		addInput(new Input("true val", dataClass));
		addInput(new Input("false val", dataClass));
		addOutput(new Output("res", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Boolean cond = getInput("cond").getData(Boolean.class);
		T val1 = getInput("true val").getData(dataClass);
		T val2 = getInput("false val").getData(dataClass);
		if(cond) getOutput("res").setData(val1);
		else getOutput("res").setData(val2);
	}

}
