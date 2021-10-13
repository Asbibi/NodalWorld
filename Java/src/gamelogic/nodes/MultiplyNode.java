package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public abstract class MultiplyNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public MultiplyNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val1", dataClass));
		addInput(new Input("val2", dataClass));
		addOutput(new Output("res", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		getOutput("res").setData(mul(val1 ,val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract T mul(T val1, T val2);


	// ========== CONCRETE SUBCLASSES ==========

	public static MultiplyNode<Integer> buildMulIntNode() {
		return new MultiplyNode<Integer>("Multiply : Int", Integer.class) {
			@Override
			protected Integer mul(Integer val1, Integer val2) {
				return val1*val2;
			}
		};
	}

	public static MultiplyNode<Double> buildMulDoubleNode() {
		return new MultiplyNode<Double>("Multiply : Double", Double.class) {
			@Override
			protected Double mul(Double val1, Double val2) {
				return val1*val2;
			}
		};
	}

}