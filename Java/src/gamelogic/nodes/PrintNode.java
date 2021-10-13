package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;

public class PrintNode<T> extends Node {
	
	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass the class object representing the type of the objects to test
	*/ 
	public PrintNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val", dataClass));
		addOutput(new Output("val", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val = getInput("val").getData(dataClass);
		System.out.println("Node Print: " + val.toString());
		getOutput("val").setData(val);
	}

}
